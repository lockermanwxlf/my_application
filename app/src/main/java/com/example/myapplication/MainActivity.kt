package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = ArrayList<Bitmap>()
        val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val cursor = this.contentResolver.query(
            collection,
            null,
            null,
            null,
            null,
            null
        )

        if (cursor != null) {
            Log.d("Cursor size", "" + cursor.count)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val uri = ContentUris.withAppendedId(collection, id)
                val stream = this.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(stream)
                data.add(bitmap)
            }
            cursor.close()
        }

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Gallery(data)
                }
            }
        }
    }
}

@Composable
fun Gallery(data: ArrayList<Bitmap>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(data) {bitmap:Bitmap ->
            Log.d("1", "1")
            var bitm by remember { mutableStateOf(bitmap.asImageBitmap()) }
            Image(
                bitmap = bitm,
                contentDescription = "image",
                modifier = Modifier.aspectRatio(1f/1f),
                contentScale = ContentScale.Crop
                )
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}