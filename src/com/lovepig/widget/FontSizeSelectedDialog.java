package com.lovepig.widget;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.NewsFontsModel;

public class FontSizeSelectedDialog extends Dialog implements OnClickListener, OnItemClickListener {
    public interface DialogListener {
        public void onClick(NewsFontsModel model);
    }

    private Button cancelBtn;
    private Button btnOne;
    private DialogListener oneListener;
    private DialogListener cancelLisenter;
    private SelectedAdapter adapter;
    private NewsFontsModel currentModel;
    private ListView listview;

    public FontSizeSelectedDialog(Context baseActivity) {
        this(baseActivity, 0, 0);
    }

    public FontSizeSelectedDialog(Context baseActivity, int type) {
        this(baseActivity, 0, type);
    }

    public FontSizeSelectedDialog(Context baseActivity, int skin, int type) {
        super(baseActivity, R.style.dialog);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.font_size_selected);
        btnOne = (Button) findViewById(R.id.btnOne);
        cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(this);
        btnOne.setOnClickListener(this);

        listview = (ListView) findViewById(R.id.listview);
        adapter = new SelectedAdapter(baseActivity);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setDivider(null);
    }

    public void setBtnText(String btnText) {
        btnOne.setText(btnText);
    }

    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btnCancel:
            dismiss();
            if (cancelLisenter != null) {
                cancelLisenter.onClick(currentModel);
            }
            break;
        case R.id.btnOne:
            dismiss();
            if (oneListener != null) {
                oneListener.onClick(currentModel);
            }
            break;
        case R.id.songcheckBox:
            for (int i = 0; i < adapter.getModelsChecked().size(); i++) {
                NewsFontsModel m = adapter.getModelsChecked().get(i);
                m.checked = false;
            }
            currentModel = (NewsFontsModel) v.getTag();
            currentModel.checked = true;
            dismiss();
            if (oneListener != null) {
                oneListener.onClick(currentModel);
            }
            break;

        }
    }

    public FontSizeSelectedDialog setDialogListener(DialogListener oneListener, DialogListener cancelListener) {
        this.oneListener = oneListener;
        this.cancelLisenter = cancelListener;
        return this;
    }

    public NewsFontsModel getSelectedModel() {
        return currentModel;
    }

    public void setListData(ArrayList<NewsFontsModel> models) {
        if (adapter != null) {
            adapter.setData(models);
            adapter.notifyDataSetChanged();
        }
    }

    class SelectedAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<NewsFontsModel> modelChecked;

        public SelectedAdapter(Context context) {
            this.context = context;
            modelChecked = new ArrayList<NewsFontsModel>();
        }

        public void setData(ArrayList<NewsFontsModel> datas) {
            modelChecked = datas;
        }

        @Override
        public int getCount() {
            return modelChecked == null ? 0 : modelChecked.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 3;
        }

        ViewHold viewhold;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.fonts_selected_item, null);
                viewhold = new ViewHold();
                viewhold.nametextView = (TextView) convertView.findViewById(R.id.gqprogItemText);
                viewhold.checkBox = (Button) convertView.findViewById(R.id.songcheckBox);
                convertView.setTag(viewhold);
            } else {
                viewhold = (ViewHold) convertView.getTag();
            }

            viewhold.mvProgramModel = modelChecked.get(position);
            viewhold.checkBox.setTag(viewhold.mvProgramModel);
            viewhold.checkBox.setOnClickListener(FontSizeSelectedDialog.this);
            viewhold.nametextView.setText(viewhold.mvProgramModel.name);
            if (viewhold.mvProgramModel.checked) {
                currentModel = viewhold.mvProgramModel;
                viewhold.checkBox.setBackgroundResource(R.drawable.mv_report_checkbox_p);
            } else {
                viewhold.checkBox.setBackgroundResource(R.drawable.mv_report_checkbox_c);
            }
            return convertView;
        }

        public class ViewHold {
            TextView nametextView;
            public NewsFontsModel mvProgramModel;
            public Button checkBox;
        }

        public ArrayList<NewsFontsModel> getModelsChecked() {
            return modelChecked;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < adapter.getModelsChecked().size(); i++) {
            NewsFontsModel m = adapter.getModelsChecked().get(i);
            if (i == position) {
                m.checked = true;
                currentModel = m;
            } else {
                m.checked = false;
            }
        }
        adapter.notifyDataSetChanged();
        if (oneListener != null) {
            oneListener.onClick(currentModel);
        }
        dismiss();
    }

}