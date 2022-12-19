package com.utkarshnew.android.Download;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0014H\u0016J\u0012\u0010\u0019\u001a\u00020\u0010*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/utkarshnew/android/Download/AesDataSource;", "Lcom/google/android/exoplayer2/upstream/DataSource;", "cipher", "Ljavax/crypto/Cipher;", "(Ljavax/crypto/Cipher;)V", "inputStream", "Ljavax/crypto/CipherInputStream;", "uri", "Landroid/net/Uri;", "addTransferListener", "", "transferListener", "Lcom/google/android/exoplayer2/upstream/TransferListener;", "close", "getUri", "open", "", "dataSpec", "Lcom/google/android/exoplayer2/upstream/DataSpec;", "read", "", "target", "", "offset", "length", "forceSkip", "bytesToSkip", "app_debug"})
public final class AesDataSource implements com.google.android.exoplayer2.upstream.DataSource {
    private final javax.crypto.Cipher cipher = null;
    private javax.crypto.CipherInputStream inputStream;
    private android.net.Uri uri;
    
    public AesDataSource(@org.jetbrains.annotations.NotNull()
    javax.crypto.Cipher cipher) {
        super();
    }
    
    @java.lang.Override()
    public long open(@org.jetbrains.annotations.NotNull()
    com.google.android.exoplayer2.upstream.DataSpec dataSpec) {
        return 0L;
    }
    
    @kotlin.jvm.Throws(exceptionClasses = {java.io.IOException.class})
    @java.lang.Override()
    public int read(@org.jetbrains.annotations.NotNull()
    byte[] target, int offset, int length) throws java.io.IOException {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.net.Uri getUri() {
        return null;
    }
    
    @java.lang.Override()
    public void addTransferListener(@org.jetbrains.annotations.NotNull()
    com.google.android.exoplayer2.upstream.TransferListener transferListener) {
    }
    
    @java.lang.Override()
    public void close() {
    }
    
    public final long forceSkip(@org.jetbrains.annotations.NotNull()
    javax.crypto.CipherInputStream $this$forceSkip, long bytesToSkip) {
        return 0L;
    }
}