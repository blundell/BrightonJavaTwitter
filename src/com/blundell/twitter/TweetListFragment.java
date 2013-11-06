package com.blundell.twitter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class TweetListFragment extends Fragment implements GetTweetsTask.OnGotTweetsListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private View emptyView;
    private List<Tweet> tweets;
    private OnTweetClickedListener listener;

    public interface OnTweetClickedListener {
        void onTweetClicked(Tweet tweet);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new GetTweetsTask(this).execute("Brighton Java");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        listView = (ListView) root.findViewById(R.id.main_list_view);
        listView.setOnItemClickListener(this);
        emptyView = root.findViewById(android.R.id.empty);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (tweets != null) {
            onGotTweets(tweets);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (OnTweetClickedListener) activity;
    }

    @Override
    public void onGotTweets(List<Tweet> tweets) {
        this.tweets = tweets;
        listView.setAdapter(new ArrayAdapter<Tweet>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, tweets));
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onTweetClicked(tweets.get(position));
    }
}