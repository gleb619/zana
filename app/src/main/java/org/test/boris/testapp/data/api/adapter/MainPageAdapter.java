package org.test.boris.testapp.data.api.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.test.boris.testapp.R;
import org.test.boris.testapp.data.api.domain.entity.other.GridItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 
 * @author manish.s
 *
 */
public class MainPageAdapter extends ArrayAdapter<GridItem> {
	Context context;
	int layoutResourceId;
	List<GridItem> data = new ArrayList<GridItem>();

	public MainPageAdapter(Context context, int layoutResourceId,
			List<GridItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new RecordHolder(row);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		GridItem item = data.get(position);

		if (item.getImage() != null) {
			holder.imageItem.setImageBitmap(item.getImage());
		}

		if (item.getIconId() > -1) {
			holder.imageItem.setImageResource(item.getIconId());
		}

		holder.txtTitle.setText(item.getTitle());

		return row;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	static class RecordHolder {
		@Bind(R.id.main_menu_item_text) TextView txtTitle;
		@Bind(R.id.main_menu_item_image) ImageView imageItem;

		public RecordHolder(View itemView) {
			ButterKnife.bind(this, itemView);
		}
	}
}