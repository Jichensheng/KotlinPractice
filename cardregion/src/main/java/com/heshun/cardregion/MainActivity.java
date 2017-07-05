package com.heshun.cardregion;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.heshun.cardregion.utils.Bitmaptest;

import java.io.File;

public class MainActivity extends AppCompatActivity {
	public static final int TAKE_PHOTO = 1;
	public static final int CHOOSE_PHOTO = 2;
	private Button mTakePhoto, mChoosePhoto;
	private ImageView picture;
	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTakePhoto = (Button) findViewById(R.id.btn_take_photo);
		mChoosePhoto = (Button) findViewById(R.id.choose_from_album);
		picture = (ImageView) findViewById(R.id.iv_picture);

		mTakePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//创建file对象，用于存储拍照后的图片；
				File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (Build.VERSION.SDK_INT >= 24) {
					imageUri = FileProvider.getUriForFile(MainActivity.this,
							"com.heshun.cardregion.fileprovider", outputImage);
				} else {
					imageUri = Uri.fromFile(outputImage);
				}

				//启动相机程序
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);
			}
		});

		mChoosePhoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
				} else {
					openAlbum();
				}
			}
		});
	}

	//打开相册
	private void openAlbum() {
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_PHOTO);

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 1:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					openAlbum();
				} else {
					Toast.makeText(this, "you denied the permission", Toast.LENGTH_SHORT).show();
				}
				break;

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case TAKE_PHOTO:
				if (resultCode == RESULT_OK) {
					try {
						Bitmap bm = Bitmaptest.fitBitmap(BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri)),1000);
						picture.setImageBitmap(bm);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				break;
			case CHOOSE_PHOTO:
				if (resultCode == RESULT_OK) {
					if (Build.VERSION.SDK_INT >= 19) {  //4.4及以上的系统使用这个方法处理图片；
						handleImageOnKitKat(data);
					} else {
						handleImageBeforeKitKat(data);  //4.4及以下的系统使用这个方法处理图片
					}
				}
			default:
				break;
		}
	}

	private void handleImageBeforeKitKat(Intent data) {
		Uri uri = data.getData();
		String imagePath = getImagePath(uri, null);
		displayImage(imagePath);
	}


	private String getImagePath(Uri uri, String selection) {
		String path = null;
		//通过Uri和selection来获取真实的图片路径
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

	private void displayImage(String imagePath) {
		if (imagePath != null) {
//			BitmapFactory.Options options=new BitmapFactory.Options();
//			options.inSampleSize=4;
			Bitmap bitmap = Bitmaptest.fitBitmap(BitmapFactory.decodeFile(imagePath),1000) ;
			picture.setImageBitmap(bitmap);
		} else {
			Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 4.4及以上的系统使用这个方法处理图片
	 *
	 * @param data
	 */
	@TargetApi(19)
	private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(this, uri)) {
			//如果document类型的Uri,则通过document来处理
			String docID = DocumentsContract.getDocumentId(uri);
			if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
				String id = docID.split(":")[1];     //解析出数字格式的id
				String selection = MediaStore.Images.Media._ID + "=" + id;

				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			} else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/piblic_downloads"), Long.valueOf(docID));

				imagePath = getImagePath(contentUri, null);

			}

		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			//如果是content类型的uri，则使用普通方式使用
			imagePath = getImagePath(uri, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			//如果是file类型的uri，直接获取路径即可
			imagePath = uri.getPath();

		}

		displayImage(imagePath);
	}
}