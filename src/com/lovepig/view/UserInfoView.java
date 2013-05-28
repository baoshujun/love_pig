package com.lovepig.view;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;

public class UserInfoView extends BaseView implements OnItemClickListener {
    TextView title;
    ListView listView;
    Button button;
    MoreAdapter adapter;

    public UserInfoView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        init();
    }

    public void init() {
        adapter = new MoreAdapter();
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        button = (Button) findViewById(R.id.rightBtn);
        button.setVisibility(GONE);
        title = (TextView) findViewById(R.id.title);
        title.setText(R.string.user_info);
        findViewById(R.id.logoImg).setVisibility(VISIBLE);
    }

    @Override
    public void onShow() {
        super.onShow();
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Math.abs(System.currentTimeMillis() - l) > t) {
            l = System.currentTimeMillis();
            if (position !=4 && !Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
            Message msg = new Message();
            msg.what = 3;
            msg.arg1 = position;
            msg.obj = button;
            manager.sendMessage(msg);
        }

    }

    class MoreAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null || Integer.valueOf(convertView.getTag().toString()) != Configs.nowOrientation) {
                convertView = LayoutInflater.from(context).inflate(R.layout.user_info_item, null);
                convertView.setTag(Configs.nowOrientation);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            ImageView newTip = (ImageView) convertView.findViewById(R.id.messageInfoTip);
            newTip.setVisibility(GONE);
            switch (position) {
            case 0://账号管理
                textView.setText(context.getString(R.string.account));
                break;
            case 1:
                textView.setText(context.getString(R.string.favorites));
                break;
            case 2:
                textView.setText(context.getString(R.string.messages));
                break;
            case 3:
                textView.setText(context.getString(R.string.downloads));
                break;
            case 4:
                textView.setText(context.getString(R.string.abouthelp));
                if (Configs.getTipSize(context) > 0) {
                    newTip.setVisibility(View.VISIBLE);
                }
                break;
            }
            LogInfo.LogOut("moreDC.getView.postion=" + position);
            return convertView;
        }

    }
}
