package com.example.spirit.noahsark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spirit.noahsark.MainActivity;
import com.example.spirit.noahsark.R;
import com.example.spirit.noahsark.constans.Player;
import com.example.spirit.noahsark.domain.Music;
import com.example.spirit.noahsark.utils.MusicUtils;
import com.example.spirit.noahsark.utils.SpUtils;

import java.util.List;

/**
 * Created by spirit on 2018/3/18.
 */

public class MusicListFragment extends Fragment {

    private ListView lv_list;
    private List<Music> musicList;
    private Intent musicBroadcastIntent;
    private int lastItem;
    private static MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicBroadcastIntent = new Intent("MusicService.MusicBroadcast");
        musicList = MusicUtils.findAllMusic(getActivity());
        activity = (MainActivity) getActivity();

        lastItem = SpUtils.getInt(getActivity(), Player.MUSIC_LAST_ITEM, 0);
        activity.setBottomText(musicList.get(lastItem).title,musicList.get(lastItem)
                .artist);
    }


    /**
     * 返回mainActivity对象
     * @return
     */
    public static MainActivity getMyActivity(){
        return activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_musiclist_fragment, null);
        lv_list = view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv_list.setAdapter(new MusicAdapter());
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                musicBroadcastIntent.putExtra("item",i);
                getContext().sendBroadcast(musicBroadcastIntent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MusicAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return musicList.size();
        }

        @Override
        public Music getItem(int i) {
            return musicList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view==null){
                 view = View.inflate(getActivity(),R.layout.music_item, null);
                 holder=new ViewHolder();
                 holder.tv_artist=view.findViewById(R.id.tv_artist);
                 holder.tv_title=view.findViewById(R.id.tv_title);
                 view.setTag(holder);
            }else {
                holder= (ViewHolder) view.getTag();
            }

            holder.tv_title.setText(musicList.get(i).title);
            holder.tv_artist.setText(musicList.get(i).artist);
            return view;
        }
    }

    class ViewHolder{
        TextView tv_title;
        TextView tv_artist;
    }
}
