package gswl.uc.com.gswl.topnews.fragment;

import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gswl.uc.com.gswl.R;

public class MThreeFragment extends BaseFragment implements OnClickListener{
	  private ImageView mHomethreeimgview_back;
	    private TextView mHomethreeimgview_textview,mHomethreeimgview_seek;
	   // private PullToRefreshListView mActivity_chooseproint_listviewss;
	  //  private PullToRefreshScrollView mScrollView;
	    ProgressDialog dialog;// 提示用户的dialog
	//    TwoFragmentAdpter twoframentadapter;
	    private RelativeLayout mHome_title_relativelayoutall;
	    private RelativeLayout mHome_title_relativeLayout;
	    private View mV1;
	    private Button mHometitleon_buttonright;
	    private Button mHometitleon_buttonleft;
	    private ImageView mHometitleon_headimg;
	    private int visiblendex = 0;   //最后的可视项索引    
	    boolean isClick = false;
	    boolean refreshType = true;

	@Override
	public View initViews() {
		// TODO Auto-generated method stub
		View rootView = View.inflate(mActivity, R.layout.fragment_mthree,
				null);
		 
		return rootView;
	}
	
	
	
	
	
	private void initDatas() {
		// TODO Auto-generated method stub
		//super.initData();
		
	
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
