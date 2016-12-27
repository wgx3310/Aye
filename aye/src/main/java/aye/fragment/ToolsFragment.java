package aye.fragment;

import android.content.Intent;
import android.view.View;

import aye.ui.PermissionActivity;
import reid.aye.R;

/**
 * Created by reid on 2016/12/17.
 */

public class ToolsFragment extends BaseFragment {

    public static ToolsFragment newInstance() {
        return new ToolsFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_tools;
    }

    @Override
    protected void initView(View root) {
        root.findViewById(R.id.tool_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PermissionActivity.class);
                startActivity(intent);
            }
        });
    }
}
