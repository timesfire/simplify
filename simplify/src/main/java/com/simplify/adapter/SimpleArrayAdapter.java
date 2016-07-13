package com.simplify.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.simplify.util.ImageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 类名称：SimpleArrayAdapter
 * 类描述： 封装的通用数据源适配器
 * 创建人：shujian
 */
public class SimpleArrayAdapter extends BaseAdapter implements OnClickListener {


    private List mData;

    private int mResource;

//    private Context mContext;

    private LayoutInflater mInflater;

    private int[] mTo;
    private String[] mFrom;
    private ViewBinder mViewBinder;
    private OnClickListener mChildViewClickListener;

    private ImageHandler imageHandler;

    public SimpleArrayAdapter setImageHandler(ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
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
    public SimpleArrayAdapter(Context context, List<?> data, int resource, String[] from, int[] to) {
        mData = data == null ? new ArrayList<>() : data;
        mResource = resource;
        mFrom = from;
        mTo = to;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public SimpleArrayAdapter(Builder builder) {
        mData = builder.mData == null? new ArrayList<>():builder.mData;
        mResource = builder.mResource;
        mFrom = builder.mFrom;
        mTo = builder.mTo;
        mInflater = (LayoutInflater) builder.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mViewBinder = builder.mViewBinder;
        mChildViewClickListener = builder.mChildViewClickListener;
    }

    public SimpleArrayAdapter(Context context, int resource, String[] from, int[] to) {
        this(context, null, resource, from, to);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }


    public void setData(List<?> mData) {
        this.mData = mData;
    }

    public List<?> getData() {
        return mData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getItem(int position) {
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

                childV.setFocusable(false);
                childV.setFocusableInTouchMode(false);

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
        Object t = mData.get(position);
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
                Object object = RetrieveHandler.getValueFromKey(from[i], t);
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
     * <p/>
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
     * <p/>
     * value cannot be used as an image resource, the value is used as an
     * image Uri.
     * <p/>
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
            imageHandler.loadImage(value, v);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (mChildViewClickListener != null) {
            mChildViewClickListener.onClick(v);
        }
    }


    public static final class Builder {

        List mData;

        int mResource;

        Context mContext;

        int[] mTo;
        String[] mFrom;


        ViewBinder mViewBinder;
        OnClickListener mChildViewClickListener;


        private ImageHandler imageHandler;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setListData(List mData) {
            this.mData = mData;
            return this;
        }

        public Builder setMapData(Map<?,?> mData) {
            ArrayList<Object> list = new ArrayList<>();
            for (Map.Entry<?,?> entry : mData.entrySet()) {
                HashMap<Object, Object> m = new HashMap<>();
                m.put("key", entry.getKey());
                m.put("value",entry.getValue());
                list.add(m);
            }
            this.mData = list;
            return this;
        }


        public Builder setmChildViewClickListener(OnClickListener mChildViewClickListener) {
            this.mChildViewClickListener = mChildViewClickListener;
            return this;
        }

        public Builder setmViewBinder(ViewBinder mViewBinder) {
            this.mViewBinder = mViewBinder;
            return this;
        }

        public Builder itemLayoutId(@LayoutRes int itemLayoutResourceId) {
            this.mResource = itemLayoutResourceId;
            return this;
        }

        public Builder from(String... from) {
            this.mFrom = from;
            return this;
        }

        public Builder to(@IdRes int... to) {
            this.mTo = to;
            return this;
        }

        public SimpleArrayAdapter build() {
            return new SimpleArrayAdapter(this);
        }


    }

}
