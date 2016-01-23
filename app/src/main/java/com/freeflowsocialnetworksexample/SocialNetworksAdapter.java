package com.freeflowsocialnetworksexample;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.core.SectionedAdapter;

import java.util.List;

/**
 * Created by bar yariv on 11/01/2016.
 */
public class SocialNetworksAdapter implements SectionedAdapter {
    private Context ctx;
    private Section section;
    private List<Integer> socialNetworks;


    public class SocialNetworksViewHolder {
        private ImageView snImg;

        public SocialNetworksViewHolder(View convertView) {
            snImg = (ImageView) convertView.findViewById(R.id.snImg);
        }
    }

    public SocialNetworksAdapter(Context ctx, List<Integer> socialNetworks) {
        this.ctx = ctx;
        this.socialNetworks = socialNetworks;
        section = new Section();
        section.setSectionTitle("Section ");

        for (int i = 0; i < socialNetworks.size(); i++) {
            section.addItem(socialNetworks.get(i));
        }
    }

    @Override
    public long getItemId(int section, int position) {
        return section * 1000 + position;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        SocialNetworksViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.social_network, parent, false);
            holder = new SocialNetworksViewHolder(convertView);
            convertView.setTag(holder);
        } else
            holder = (SocialNetworksViewHolder) convertView.getTag();

        holder.snImg.setImageResource((int) this.section.getData().get(position));
        return convertView;
    }

    @Override
    public View getHeaderViewForSection(int section, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getNumberOfSections() {
        if (section.getData().size() == 0) return 0;
        return 1;
    }

    @Override
    public Section getSection(int index) {
        return section;
    }

    @Override
    public Class[] getViewTypes() {

        return new Class[]{TextView.class, TextView.class};
    }

    @Override
    public Class getViewType(FreeFlowItem proxy) {
        return TextView.class;
    }

    @Override
    public boolean shouldDisplaySectionHeaders() {
        return false;
    }

}

