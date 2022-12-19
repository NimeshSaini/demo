package com.utkarshnew.android.Download

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.utkarshnew.android.Download.Audio.AudioPlayerService
import com.utkarshnew.android.Utils.Helper
import java.io.File
import java.io.IOException
import javax.crypto.Cipher
import javax.crypto.CipherInputStream

class AesDataSource(
    private val cipher: Cipher
) : DataSource {

    private var inputStream: CipherInputStream? = null
    private lateinit var uri: Uri

    override fun open(dataSpec: DataSpec): Long {
        uri = dataSpec.uri
        uri.path ?: return 0

        val file = File(uri.path!!).canonicalFile
        inputStream = CipherInputStream(file.inputStream(), cipher)
        if (dataSpec.position != 0L) {
            inputStream?.forceSkip(dataSpec.position)
        }

        return dataSpec.length
    }

    @Throws(IOException::class)
    override fun read(target: ByteArray, offset: Int, length: Int): Int =
        if (length == 0) {
            Log.d("readdata", "" + length)
            0
        } else {
            Log.d("readdata", "" + length)
            inputStream?.read(target, offset, length) ?: 0

        }

    override fun getUri(): Uri? {
        return uri
    }

    override fun addTransferListener(transferListener: TransferListener) {}


    override fun close() {
        inputStream?.close()
    }

    fun CipherInputStream.forceSkip(bytesToSkip: Long): Long {
        var processedBytes = 0L
        while (processedBytes < bytesToSkip) {
            read()
            processedBytes++
        }

        return processedBytes
    }



}