package com.github.baselibrary.tagcloud.demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.baselibrary.R;
import com.github.baselibrary.tagcloud.base.TagCloudView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestTagCloudFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestTagCloudFragment extends Fragment {
    private View rootView;
    private TagCloudView fragmentTagcloud;

    public TestTagCloudFragment() {
        // Required empty public constructor
    }

    public static TestTagCloudFragment newInstance() {
        TestTagCloudFragment fragment = new TestTagCloudFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test_tag_cloud, container, false);
        instantiationViews();
        TextTagsAdapter adapter = new TextTagsAdapter(new String[20]);
        fragmentTagcloud.setAdapter(adapter);
        return rootView;
    }

    private void instantiationViews() {
        fragmentTagcloud = (TagCloudView) rootView.findViewById(R.id.fragment_tagcloud);
    }
}
