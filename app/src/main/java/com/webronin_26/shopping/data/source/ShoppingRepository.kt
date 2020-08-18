package com.webronin_26.shopping.data.source

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.jakewharton.disklrucache.DiskLruCache
import com.webronin_26.shopping.data.remote.RequestInterface
import com.webronin_26.shopping.data.remote.Response
import com.webronin_26.shopping.data.remote.Result
import com.webronin_26.shopping.data.remote.STATUS_SUCCESS
import com.webronin_26.shopping.data.util.HashkeyMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.nio.ByteBuffer

private const val BASE_URL = "http://10.0.2.2:1323"
private const val PICTURE_BASE_URL = "https://i.imgur.com/"

private const val IMAGE_FILE_NAME = "IMAGE_CACHE"

class ShoppingRepository(val context: Context) {

    // application picture cache manager
    private val cacheSize : Int = ((Runtime.getRuntime().maxMemory() / 1024) / 4).toInt()
    private val lruCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            if (value == null) { return 0 }
            return (value.byteCount).div(1024)
        }
    }

    // application picture diskCache manager
    private val diskCacheSize: Long = 1024 * 1024 * 50
    private var diskLruCache: DiskLruCache? = null

    init {
        try {
            val cacheFilePath = File(context.cacheDir, IMAGE_FILE_NAME)
            diskLruCache = DiskLruCache.open(cacheFilePath, 1,1, diskCacheSize)
        }catch (e: Exception) {
            // do nothing
        }
    }

    suspend fun getImage(url: String): Bitmap? = withContext(Dispatchers.IO) {

        val hashUrl = HashkeyMD5().hashKeyFromString(url)
        var bitmap: Bitmap?

        bitmap = lruCache.get(hashUrl)
        if (bitmap != null) { return@withContext bitmap }

        bitmap = downloadImage(url)
        if (bitmap == null) { return@withContext bitmap }
        lruCache.put(hashUrl, bitmap)

        return@withContext bitmap
    }

    private fun getImageInFile(hashUrl: String): Bitmap? {

        var bitmap: Bitmap? = null

        try {
            val snapshot = diskLruCache?.get(hashUrl)

            if (snapshot != null) {
                val inputStream = snapshot.getInputStream(0)
                bitmap = BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            // do nothing
        }

        return bitmap
    }

    private fun saveImageInFile(hashUrl: String, bitmap: Bitmap) {

        var outputStream: OutputStream? = null

        try {
            val editor = diskLruCache?.edit(hashUrl)
            outputStream = editor?.newOutputStream(0)

            val byteBuffer = ByteBuffer.allocate(bitmap.byteCount)
            bitmap.copyPixelsFromBuffer(byteBuffer)

            try {
                outputStream?.write(byteBuffer.array())
                editor?.commit()
            } catch (e: Exception) {
                editor?.abort()
            }
        } catch (e: Exception) {
            // do nothing
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    // do nothing
                }
            }
        }
    }

    private suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val responseBody = Retrofit.Builder()
                .baseUrl(PICTURE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestInterface::class.java)
                .downloadPicture(url)

            val bytes = responseBody.bytes()
            return@withContext BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    private fun getRetrofitInstance(): RequestInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestInterface::class.java)
    }

    suspend fun refreshMainPage(): Result<Response.MainResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().mainRequest()

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }

    suspend fun search(searchString: String): Result<Response.SearchResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().searchRequest(searchString)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }

    suspend fun query(id: Int): Result<Response.QueryResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryRequest(id)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }

    suspend fun buy(token: String, id: Int, number: Int): Result<Response.BuyProductResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().buyRequest(token, id, number)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }

    suspend fun login(email: String, password: String): Result<Response.LoginResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().loginRequest(email, password)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }

    suspend fun logout(token: String): Result<Response.LogoutResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().logoutRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.Error(Exception(e.toString()))
        }
    }
}