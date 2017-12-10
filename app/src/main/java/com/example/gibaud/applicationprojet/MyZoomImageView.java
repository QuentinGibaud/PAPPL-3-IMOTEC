package com.example.gibaud.applicationprojet;

//Liste des imports
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * Gère la fonction de zoom sur l'arbre de décision
 * Classe reprise de la précedente version
 * @author Quentin GIBAUD, Kevin CLEMENS
 *
 */

public class MyZoomImageView extends android.support.v7.widget.AppCompatImageView {

    /**
     * Déclaration des attributs
     */
    //Taille de l'image
    private int imgHeight;
    private int imgWidth;
    private int intrinsicHeight;
    private int intrinsicWidth;
    //Mise à l'échelle
    private float mMaxScale = 1.8f;
    private float mMinScale = 0.30f;
    private Matrix matrix = new Matrix();
    private Matrix currentMatrix = new Matrix();
    private long firstTouchTime = 0;
    private int intervalTime = 250;
    private PointF firstPointF;

    /**
     * Constructeur à 1 paramètre
     * @param context , le layout de base
     */
    public MyZoomImageView(Context context) {
        super(context);
        initUI();
    }

    /**
     * Constructeur à 2 paramètres
     * @param context , le layout de base
     * @param attrs , attribut de mise à l'échelle
     */
    public MyZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    /**
     * Constructeur à 3 paramètres
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyZoomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initUI();
    }

    /**
     * UI
     */
    private void initUI() {

        this.setScaleType(ScaleType.FIT_CENTER);
        this.setOnTouchListener(new TouchListener());

        getImageViewWidthHeight();
        getIntrinsicWidthHeight();
    }

    /**
     * Mise à l'échelle
     */
    private void getIntrinsicWidthHeight() {
        Drawable drawable = this.getDrawable();

        intrinsicHeight = drawable.getIntrinsicHeight();
        intrinsicWidth = drawable.getIntrinsicWidth();
    }

    /**
     * Gère le zoom avec les doigts
     */
    private final class TouchListener implements OnTouchListener {


        private int mode = 0;
        private static final int MODE_DRAG = 1;
        private static final int MODE_ZOOM = 2;
        private PointF startPoint = new PointF();
        private float startDis;
        private PointF midPoint;

        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = MODE_DRAG;
                    currentMatrix.set(getImageMatrix());
                    startPoint.set(event.getX(), event.getY());
                    matrix.set(currentMatrix);
                    makeImageViewFit();
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == MODE_DRAG) {
                        // System.out.println("ACTION_MOVE_____MODE_DRAG");
                        float dx = event.getX() - startPoint.x;
                        float dy = event.getY() - startPoint.y;
                        matrix.set(currentMatrix);
                        float[] values = new float[9];
                        matrix.getValues(values);
                        dx = checkDxBound(values, dx);
                        dy = checkDyBound(values, dy);
                        matrix.postTranslate(dx, dy);

                    }
                    else if (mode == MODE_ZOOM) {
                        float endDis = distance(event);
                        if (endDis > 10f) {
                            float scale = endDis / startDis;
                            matrix.set(currentMatrix);

                            float[] values = new float[9];
                            matrix.getValues(values);

                            scale = checkFitScale(scale, values);

                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    setDoubleTouchEvent(event);

                case MotionEvent.ACTION_POINTER_UP:
                    // System.out.println("ACTION_POINTER_UP");
                    mode = 0;
                    // matrix.set(currentMatrix);
                    float[] values = new float[9];
                    matrix.getValues(values);
                    makeImgCenter(values);
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    // System.out.println("ACTION_POINTER_DOWN");
                    mode = MODE_ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) {
                        midPoint = mid(event);
                        currentMatrix.set(getImageMatrix());
                    }
                    break;
            }
            setImageMatrix(matrix);
            return true;
        }


        private float distance(MotionEvent event) {
            float dx = event.getX(1) - event.getX(0);
            float dy = event.getY(1) - event.getY(0);
            return (float) Math.sqrt(dx * dx + dy * dy);
        }

        private PointF mid(MotionEvent event) {
            float midX = (event.getX(1) + event.getX(0)) / 2;
            float midY = (event.getY(1) + event.getY(0)) / 2;
            return new PointF(midX, midY);
        }

        /**
         * Limites de l'écran en ordonnée
         * @param values
         * @param dy
         * @return
         */
        private float checkDyBound(float[] values, float dy) {

            float height = imgHeight;
            if (intrinsicHeight * values[Matrix.MSCALE_Y] < height)
                return 0;
            if (values[Matrix.MTRANS_Y] + dy > 0)
                dy = -values[Matrix.MTRANS_Y];
            else if (values[Matrix.MTRANS_Y] + dy < -(intrinsicHeight
                    * values[Matrix.MSCALE_Y] - height))
                dy = -(intrinsicHeight * values[Matrix.MSCALE_Y] - height)
                        - values[Matrix.MTRANS_Y];
            return dy;
        }

        /**
         * Limites de l'écran en abscisse
         * @param values
         * @param dx
         * @return
         */
        private float checkDxBound(float[] values, float dx) {

            float width = imgWidth;
            if (intrinsicWidth * values[Matrix.MSCALE_X] < width)
                return 0;
            if (values[Matrix.MTRANS_X] + dx > 0)
                dx = -values[Matrix.MTRANS_X];
            else if (values[Matrix.MTRANS_X] + dx < -(intrinsicWidth
                    * values[Matrix.MSCALE_X] - width))
                dx = -(intrinsicWidth * values[Matrix.MSCALE_X] - width)
                        - values[Matrix.MTRANS_X];
            return dx;
        }

        /**
         * Mettre à l'échelle
         * @param scale
         * @param values
         * @return
         */
        private float checkFitScale(float scale, float[] values) {
            if (scale * values[Matrix.MSCALE_X] > mMaxScale)
                scale = mMaxScale / values[Matrix.MSCALE_X];
            if (scale * values[Matrix.MSCALE_X] < mMinScale)
                scale = mMinScale / values[Matrix.MSCALE_X];
            return scale;
        }

        /**
         * Centrer l'image
         * @param values
         *
         */
        private void makeImgCenter(float[] values) {

            float zoomY = intrinsicHeight * values[Matrix.MSCALE_Y];
            float zoomX = intrinsicWidth * values[Matrix.MSCALE_X];
            float leftY = values[Matrix.MTRANS_Y];
            float leftX = values[Matrix.MTRANS_X];
            float rightY = leftY + zoomY;
            float rightX = leftX + zoomX;

            if (zoomY < imgHeight) {
                float marY = (imgHeight - zoomY) / 2.0f;
                matrix.postTranslate(0, marY - leftY);
            }

            if (zoomX < imgWidth) {

                float marX = (imgWidth - zoomX) / 2.0f;
                matrix.postTranslate(marX - leftX, 0);

            }

            if (zoomY >= imgHeight) {
                if (leftY > 0) {
                    matrix.postTranslate(0, -leftY);
                }
                if (rightY < imgHeight) {
                    matrix.postTranslate(0, imgHeight - rightY);
                }
            }

            if (zoomX >= imgWidth) {
                if (leftX > 0) {
                    matrix.postTranslate(-leftX, 0);
                }
                if (rightX < imgWidth) {
                    matrix.postTranslate(imgWidth - rightX, 0);
                }
            }
        }

    }

    /**
     * ImageView
     */
    private void getImageViewWidthHeight() {
        ViewTreeObserver vto2 = getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imgWidth = getWidth();
                imgHeight = getHeight();

            }
        });
    }

    /**
     * mageView
     */
    private void makeImageViewFit() {
        if (getScaleType() != ScaleType.MATRIX) {
            setScaleType(ScaleType.MATRIX);

            matrix.postScale(1.0f, 1.0f, imgWidth / 2, imgHeight / 2);
        }
    }

    /**
     * Gérer les évenements
     * @param event
     */
    private void setDoubleTouchEvent(MotionEvent event) {

        float values[] = new float[9];
        matrix.getValues(values);

        long currentTime = System.currentTimeMillis();

        if (currentTime - firstTouchTime >= intervalTime) {
            firstTouchTime = currentTime;
            firstPointF = new PointF(event.getX(), event.getY());
        } else {

            if (Math.abs(event.getX() - firstPointF.x) < 30f
                    && Math.abs(event.getY() - firstPointF.y) < 30f) {

                if (values[Matrix.MSCALE_X] < mMaxScale) {
                    matrix.postScale(mMaxScale / values[Matrix.MSCALE_X],
                            mMaxScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                } else {
                    matrix.postScale(mMinScale / values[Matrix.MSCALE_X],
                            mMinScale / values[Matrix.MSCALE_X], event.getX(),
                            event.getY());
                }
            }

        }
    }

    /**
     *
     * @param mMaxScale
     * @param mMinScale
     */
    public void setPicZoomHeightWidth(float mMaxScale, float mMinScale) {
        this.mMaxScale = mMaxScale;
        this.mMinScale = mMinScale;
    }

}
