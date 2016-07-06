package com.simplify.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.simplify.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * 类名称：SimpleArrayAdapter
 * 类描述： 封装的通用数据源适配器
 * 创建人：shujian
 */
public class SimpleArrayAdapter<T extends BaseBean> extends BaseAdapter implements OnClickListener {


    private List<T> mData;

    private int mResource;

    private Context mContext;

    private LayoutInflater mInflater;

    private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private OnClickListener mChildViewClickListener;

    private ImageLoader imageLoader;

    public SimpleArrayAdapter<T> setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of T. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     *                 in the from parameter.
     */
    public SimpleArrayAdapter(Context context, List<T> data,
                              int resource, String[] from, int[] to, ViewGroup parentV) {
        mData = data == null ? new ArrayList<T>() : data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * TEST TODO
     *
     * @param context
     * @param resource
     * @param from
     * @param to
     * @param parentV
     */
    public SimpleArrayAdapter(Context context,
                              int resource, String[] from, int[] to, ViewGroup parentV) {
        List data = new ArrayList();
        for (int i = 0; i < 10; i++) {
            BaseBean baseBean = new BaseBean();
            data.add(baseBean);
        }
        mData = data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }


    public void setData(List<T> mData) {
        this.mData = mData;
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return mData.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = createViewFromResource(position, convertView, parent);
        } else {
            v = convertView;
        }
        bindView(position, v);
        return v;
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(mResource, parent, false);
        List<View> viewHandler = new ArrayList<View>();
        for (int i = 0; i < mTo.length; i++) {
            View childV = v.findViewById(mTo[i]);
            if (childV.isClickable()) {
                childV.setOnClickListener(this);
            }
            viewHandler.add(v.findViewById(mTo[i]));
        }
        v.setTag(viewHandler);
        return v;
    }

    private void bindView(int position, View view) {
        final ViewBinder binder = mViewBinder;
        final int count = mTo.length;
        final String[] from = mFrom;
        List<View> vHandler = (List<View>) view.getTag();
        T t = mData.get(position);
        for (int i = 0; i < count; i++) {
            View v = vHandler.get(i);
            //为可点击view 设置数据
            if (v.isClickable()) {
                v.setTag(t);
            }
            //view没有相对应的赋值字段
            if (i > from.length - 1) continue;
            boolean bound = false;
            if (binder != null) {
                bound = binder.setViewValue(v, t, from[i]);
            }

            if (!bound) {
                Object object = t.get(from[i]);
                String text = "";
                if (object != null) {
                    text = object.toString();
                }
                if (v instanceof TextView) {
                    setViewText((TextView) v, text);
                } else if (v instanceof ImageView) {
                    setViewImage((ImageView) v, text);
                } else {
                    throw new IllegalStateException(v.getClass().getName() + " is not a " +
                            " view that can be bounds by this SimpleCursorAdapter");
                }
            }

        }
    }

    /**
     * Returns the {@link ViewBinder} used to bind data to views.
     *
     * @return a ViewBinder or null if the binder does not exist
     */
    public ViewBinder getViewBinder() {
        return mViewBinder;
    }

    /**
     * Sets the binder used to bind data to views.
     *
     * @param viewBinder the binder used to bind data to views, can be null to
     *                   remove the existing binder
     * @see #getViewBinder()
     */
    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
    }


    public OnClickListener getChildViewClickListener() {
        return mChildViewClickListener;
    }

    public void setChildViewClickListener(OnClickListener childViewClickListener) {
        this.mChildViewClickListener = childViewClickListener;
    }

    /**
     * Called by bindView() to set the text for a TextView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an TextView.
     *
     * Intended to be overridden by Adapters that need to filter strings
     * retrieved from the database.
     *
     * @param v    TextView to receive text
     * @param text the text to be set for the TextView
     */
    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    /**
     * Called by bindView() to set the image for an ImageView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an ImageView.
     * By default, the value will be treated as an image resource. If the
     *
     * value cannot be used as an image resource, the value is used as an
     * image Uri.
     *
     * Intended to be overridden by Adapters that need to filter strings
     * retrieved from the database.
     *
     * @param v     ImageView to receive an image
     * @param value the value retrieved from the T
     */
    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            imageLoader.loadImage(value, v);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mChildViewClickListener != null) {
            mChildViewClickListener.onClick(v);
        }
    }

}
