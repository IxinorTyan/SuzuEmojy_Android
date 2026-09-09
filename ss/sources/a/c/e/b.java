package a.c.e;

/* JADX INFO: loaded from: classes.dex */
public final class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final a.c.e.b.a f90a;

    interface a {
        boolean a(android.view.MotionEvent motionEvent);
    }

    /* JADX INFO: renamed from: a.c.e.b$b, reason: collision with other inner class name */
    static class C0003b implements a.c.e.b.a {
        private static final int v = android.view.ViewConfiguration.getLongPressTimeout();
        private static final int w = android.view.ViewConfiguration.getTapTimeout();
        private static final int x = android.view.ViewConfiguration.getDoubleTapTimeout();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private int f91a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        private int f92b;

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        private int f93c;
        private int d;
        private final android.os.Handler e;
        final android.view.GestureDetector.OnGestureListener f;
        android.view.GestureDetector.OnDoubleTapListener g;
        boolean h;
        boolean i;
        private boolean j;
        private boolean k;
        private boolean l;
        android.view.MotionEvent m;
        private android.view.MotionEvent n;
        private boolean o;
        private float p;
        private float q;
        private float r;
        private float s;
        private boolean t;
        private android.view.VelocityTracker u;

        /* JADX INFO: renamed from: a.c.e.b$b$a */
        private class a extends android.os.Handler {
            a() {
            }

            a(android.os.Handler handler) {
                super(handler.getLooper());
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                int i = message.what;
                if (i == 1) {
                    a.c.e.b.C0003b c0003b = a.c.e.b.C0003b.this;
                    c0003b.f.onShowPress(c0003b.m);
                    return;
                }
                if (i == 2) {
                    a.c.e.b.C0003b.this.a();
                    return;
                }
                if (i != 3) {
                    throw new java.lang.RuntimeException("Unknown message " + message);
                }
                a.c.e.b.C0003b c0003b2 = a.c.e.b.C0003b.this;
                android.view.GestureDetector.OnDoubleTapListener onDoubleTapListener = c0003b2.g;
                if (onDoubleTapListener != null) {
                    if (c0003b2.h) {
                        c0003b2.i = true;
                    } else {
                        onDoubleTapListener.onSingleTapConfirmed(c0003b2.m);
                    }
                }
            }
        }

        C0003b(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler) {
            if (handler != null) {
                this.e = new a.c.e.b.C0003b.a(handler);
            } else {
                this.e = new a.c.e.b.C0003b.a();
            }
            this.f = onGestureListener;
            if (onGestureListener instanceof android.view.GestureDetector.OnDoubleTapListener) {
                a((android.view.GestureDetector.OnDoubleTapListener) onGestureListener);
            }
            a(context);
        }

        private void a(android.content.Context context) {
            if (context == null) {
                throw new java.lang.IllegalArgumentException("Context must not be null");
            }
            if (this.f == null) {
                throw new java.lang.IllegalArgumentException("OnGestureListener must not be null");
            }
            this.t = true;
            android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
            int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
            int scaledDoubleTapSlop = viewConfiguration.getScaledDoubleTapSlop();
            this.f93c = viewConfiguration.getScaledMinimumFlingVelocity();
            this.d = viewConfiguration.getScaledMaximumFlingVelocity();
            this.f91a = scaledTouchSlop * scaledTouchSlop;
            this.f92b = scaledDoubleTapSlop * scaledDoubleTapSlop;
        }

        private boolean a(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, android.view.MotionEvent motionEvent3) {
            if (!this.l || motionEvent3.getEventTime() - motionEvent2.getEventTime() > x) {
                return false;
            }
            int x2 = ((int) motionEvent.getX()) - ((int) motionEvent3.getX());
            int y = ((int) motionEvent.getY()) - ((int) motionEvent3.getY());
            return (x2 * x2) + (y * y) < this.f92b;
        }

        private void b() {
            this.e.removeMessages(1);
            this.e.removeMessages(2);
            this.e.removeMessages(3);
            this.u.recycle();
            this.u = null;
            this.o = false;
            this.h = false;
            this.k = false;
            this.l = false;
            this.i = false;
            if (this.j) {
                this.j = false;
            }
        }

        private void c() {
            this.e.removeMessages(1);
            this.e.removeMessages(2);
            this.e.removeMessages(3);
            this.o = false;
            this.k = false;
            this.l = false;
            this.i = false;
            if (this.j) {
                this.j = false;
            }
        }

        void a() {
            this.e.removeMessages(3);
            this.i = false;
            this.j = true;
            this.f.onLongPress(this.m);
        }

        public void a(android.view.GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.g = onDoubleTapListener;
        }

        @Override // a.c.e.b.a
        public boolean a(android.view.MotionEvent motionEvent) {
            boolean zOnDoubleTap;
            android.view.MotionEvent motionEvent2;
            boolean zOnFling;
            android.view.GestureDetector.OnDoubleTapListener onDoubleTapListener;
            int action = motionEvent.getAction();
            if (this.u == null) {
                this.u = android.view.VelocityTracker.obtain();
            }
            this.u.addMovement(motionEvent);
            int i = action & 255;
            boolean z = i == 6;
            int actionIndex = z ? motionEvent.getActionIndex() : -1;
            int pointerCount = motionEvent.getPointerCount();
            float x2 = 0.0f;
            float y = 0.0f;
            for (int i2 = 0; i2 < pointerCount; i2++) {
                if (actionIndex != i2) {
                    x2 += motionEvent.getX(i2);
                    y += motionEvent.getY(i2);
                }
            }
            float f = z ? pointerCount - 1 : pointerCount;
            float f2 = x2 / f;
            float f3 = y / f;
            if (i == 0) {
                if (this.g == null) {
                    zOnDoubleTap = false;
                } else {
                    boolean zHasMessages = this.e.hasMessages(3);
                    if (zHasMessages) {
                        this.e.removeMessages(3);
                    }
                    android.view.MotionEvent motionEvent3 = this.m;
                    if (motionEvent3 == null || (motionEvent2 = this.n) == null || !zHasMessages || !a(motionEvent3, motionEvent2, motionEvent)) {
                        this.e.sendEmptyMessageDelayed(3, x);
                        zOnDoubleTap = false;
                    } else {
                        this.o = true;
                        zOnDoubleTap = this.g.onDoubleTap(this.m) | false | this.g.onDoubleTapEvent(motionEvent);
                    }
                }
                this.p = f2;
                this.r = f2;
                this.q = f3;
                this.s = f3;
                android.view.MotionEvent motionEvent4 = this.m;
                if (motionEvent4 != null) {
                    motionEvent4.recycle();
                }
                this.m = android.view.MotionEvent.obtain(motionEvent);
                this.k = true;
                this.l = true;
                this.h = true;
                this.j = false;
                this.i = false;
                if (this.t) {
                    this.e.removeMessages(2);
                    this.e.sendEmptyMessageAtTime(2, this.m.getDownTime() + ((long) w) + ((long) v));
                }
                this.e.sendEmptyMessageAtTime(1, this.m.getDownTime() + ((long) w));
                return zOnDoubleTap | this.f.onDown(motionEvent);
            }
            if (i == 1) {
                this.h = false;
                android.view.MotionEvent motionEventObtain = android.view.MotionEvent.obtain(motionEvent);
                if (this.o) {
                    zOnFling = this.g.onDoubleTapEvent(motionEvent) | false;
                } else {
                    if (this.j) {
                        this.e.removeMessages(3);
                        this.j = false;
                    } else if (this.k) {
                        boolean zOnSingleTapUp = this.f.onSingleTapUp(motionEvent);
                        if (this.i && (onDoubleTapListener = this.g) != null) {
                            onDoubleTapListener.onSingleTapConfirmed(motionEvent);
                        }
                        zOnFling = zOnSingleTapUp;
                    } else {
                        android.view.VelocityTracker velocityTracker = this.u;
                        int pointerId = motionEvent.getPointerId(0);
                        velocityTracker.computeCurrentVelocity(1000, this.d);
                        float yVelocity = velocityTracker.getYVelocity(pointerId);
                        float xVelocity = velocityTracker.getXVelocity(pointerId);
                        if (java.lang.Math.abs(yVelocity) > this.f93c || java.lang.Math.abs(xVelocity) > this.f93c) {
                            zOnFling = this.f.onFling(this.m, motionEvent, xVelocity, yVelocity);
                        }
                    }
                    zOnFling = false;
                }
                android.view.MotionEvent motionEvent5 = this.n;
                if (motionEvent5 != null) {
                    motionEvent5.recycle();
                }
                this.n = motionEventObtain;
                android.view.VelocityTracker velocityTracker2 = this.u;
                if (velocityTracker2 != null) {
                    velocityTracker2.recycle();
                    this.u = null;
                }
                this.o = false;
                this.i = false;
                this.e.removeMessages(1);
                this.e.removeMessages(2);
            } else {
                if (i != 2) {
                    if (i == 3) {
                        b();
                        return false;
                    }
                    if (i == 5) {
                        this.p = f2;
                        this.r = f2;
                        this.q = f3;
                        this.s = f3;
                        c();
                        return false;
                    }
                    if (i != 6) {
                        return false;
                    }
                    this.p = f2;
                    this.r = f2;
                    this.q = f3;
                    this.s = f3;
                    this.u.computeCurrentVelocity(1000, this.d);
                    int actionIndex2 = motionEvent.getActionIndex();
                    int pointerId2 = motionEvent.getPointerId(actionIndex2);
                    float xVelocity2 = this.u.getXVelocity(pointerId2);
                    float yVelocity2 = this.u.getYVelocity(pointerId2);
                    for (int i3 = 0; i3 < pointerCount; i3++) {
                        if (i3 != actionIndex2) {
                            int pointerId3 = motionEvent.getPointerId(i3);
                            if ((this.u.getXVelocity(pointerId3) * xVelocity2) + (this.u.getYVelocity(pointerId3) * yVelocity2) < 0.0f) {
                                this.u.clear();
                                return false;
                            }
                        }
                    }
                    return false;
                }
                if (this.j) {
                    return false;
                }
                float f4 = this.p - f2;
                float f5 = this.q - f3;
                if (this.o) {
                    return false | this.g.onDoubleTapEvent(motionEvent);
                }
                if (!this.k) {
                    if (java.lang.Math.abs(f4) < 1.0f && java.lang.Math.abs(f5) < 1.0f) {
                        return false;
                    }
                    boolean zOnScroll = this.f.onScroll(this.m, motionEvent, f4, f5);
                    this.p = f2;
                    this.q = f3;
                    return zOnScroll;
                }
                int i4 = (int) (f2 - this.r);
                int i5 = (int) (f3 - this.s);
                int i6 = (i4 * i4) + (i5 * i5);
                if (i6 > this.f91a) {
                    zOnFling = this.f.onScroll(this.m, motionEvent, f4, f5);
                    this.p = f2;
                    this.q = f3;
                    this.k = false;
                    this.e.removeMessages(3);
                    this.e.removeMessages(1);
                    this.e.removeMessages(2);
                } else {
                    zOnFling = false;
                }
                if (i6 > this.f91a) {
                    this.l = false;
                }
            }
            return zOnFling;
        }
    }

    static class c implements a.c.e.b.a {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        private final android.view.GestureDetector f95a;

        c(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler) {
            this.f95a = new android.view.GestureDetector(context, onGestureListener, handler);
        }

        @Override // a.c.e.b.a
        public boolean a(android.view.MotionEvent motionEvent) {
            return this.f95a.onTouchEvent(motionEvent);
        }
    }

    public b(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public b(android.content.Context context, android.view.GestureDetector.OnGestureListener onGestureListener, android.os.Handler handler) {
        this.f90a = android.os.Build.VERSION.SDK_INT > 17 ? new a.c.e.b.c(context, onGestureListener, handler) : new a.c.e.b.C0003b(context, onGestureListener, handler);
    }

    public boolean a(android.view.MotionEvent motionEvent) {
        return this.f90a.a(motionEvent);
    }
}
