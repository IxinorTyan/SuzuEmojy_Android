package com.android.billingclient.api;

/* JADX INFO: loaded from: classes.dex */
final class p implements java.util.concurrent.ThreadFactory {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final java.util.concurrent.ThreadFactory f435a = java.util.concurrent.Executors.defaultThreadFactory();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final java.util.concurrent.atomic.AtomicInteger f436b = new java.util.concurrent.atomic.AtomicInteger(1);

    p(com.android.billingclient.api.d dVar) {
    }

    @Override // java.util.concurrent.ThreadFactory
    public final java.lang.Thread newThread(java.lang.Runnable runnable) {
        java.lang.Thread threadNewThread = this.f435a.newThread(runnable);
        int andIncrement = this.f436b.getAndIncrement();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(30);
        sb.append("PlayBillingLibrary-");
        sb.append(andIncrement);
        threadNewThread.setName(sb.toString());
        return threadNewThread;
    }
}
