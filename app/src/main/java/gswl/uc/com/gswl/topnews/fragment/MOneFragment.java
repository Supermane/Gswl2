package gswl.uc.com.gswl.topnews.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import gswl.uc.com.gswl.R;
import gswl.uc.com.gswl.topnews.ChannelActivity;
import gswl.uc.com.gswl.topnews.adapter.NewsFragmentPagerAdapter;
import gswl.uc.com.gswl.topnews.app.AppApplication;
import gswl.uc.com.gswl.topnews.bean.ChannelItem;
import gswl.uc.com.gswl.topnews.bean.ChannelManage;
import gswl.uc.com.gswl.topnews.tool.BaseTools;
import gswl.uc.com.gswl.topnews.view.ColumnHorizontalScrollView;
import gswl.uc.com.gswl.topnews.view.DrawerView;

public class MOneFragment extends BaseFragment implements OnClickListener{
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

	    
		/** 自定义HorizontalScrollView */
		private ColumnHorizontalScrollView mColumnHorizontalScrollView;
		LinearLayout mRadioGroup_content;
		LinearLayout ll_more_columns;
		RelativeLayout rl_column;
		private ViewPager mViewPager;
		private ImageView button_more_columns;
		/** 用户选择的新闻分类列表*/
		private ArrayList<ChannelItem> userChannelList=new ArrayList<ChannelItem>();
		/** 当前选中的栏目*/
		private int columnSelectIndex = 0;
		/** 左阴影部分*/
		public ImageView shade_left;
		/** 右阴影部分 */
		public ImageView shade_right;
		/** 屏幕宽度 */
		private int mScreenWidth = 0;
		/** Item宽度 */
		private int mItemWidth = 0;
		private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		
		protected SlidingMenu side_drawer;
		
		/** head 头部 的中间的loading*/
		private ProgressBar top_progress;
		/** head 头部 中间的刷新按钮*/
		private ImageView top_refresh;
		/** head 头部 的左侧菜单 按钮*/
		private ImageView top_head;
		/** head 头部 的右侧菜单 按钮*/
		private ImageView top_more;
		/** 请求CODE */
		public final static int CHANNELREQUEST = 1;
		/** 调整返回的RESULTCODE */
		public final static int CHANNELRESULT = 10;
	@Override
	public View initViews() {
		// TODO Auto-generated method stub
		View rootView = View.inflate(mActivity, R.layout.fragment_mone,
				null);
		mColumnHorizontalScrollView =  (ColumnHorizontalScrollView)rootView.findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) rootView.findViewById(R.id.mRadioGroup_content);
		ll_more_columns = (LinearLayout)rootView. findViewById(R.id.ll_more_columns);
		rl_column = (RelativeLayout) rootView.findViewById(R.id.rl_column);
		button_more_columns = (ImageView) rootView.findViewById(R.id.button_more_columns);
		mViewPager = (ViewPager)rootView. findViewById(R.id.mViewPager);
		shade_left = (ImageView)rootView. findViewById(R.id.shade_left);
		shade_right = (ImageView) rootView.findViewById(R.id.shade_right);
		top_head = (ImageView) rootView.findViewById(R.id.top_head);
		top_more = (ImageView) rootView.findViewById(R.id.top_more);
		top_refresh = (ImageView)rootView. findViewById(R.id.top_refresh);
		top_progress = (ProgressBar) rootView.findViewById(R.id.top_progress);
		mScreenWidth = BaseTools.getWindowsWidth(getActivity());
		mItemWidth = mScreenWidth / 7;// 一个Item宽度为屏幕的1/7
		
		button_more_columns.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent_channel = new  Intent(getActivity(), ChannelActivity.class);
				startActivityForResult(intent_channel, CHANNELREQUEST);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
		top_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(side_drawer.isMenuShowing()){
					side_drawer.showContent();
				}else{
					side_drawer.showMenu();
				}
			}
		});
		top_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(side_drawer.isSecondaryMenuShowing()){
					side_drawer.showContent();
				}else{
					side_drawer.showSecondaryMenu();
				}
			}
		});
		setChangelView();
		
		initSlidingMenu();
		return rootView;
	}
	
	/** 初始化layout控件*/
	private void initView() {
	
	}
	/** 
	 *  当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();
	}
	/** 获取Column栏目 数据*/
	private void initColumnData() {
		userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
	}

	/** 
	 *  初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count =  userChannelList.size();
		mColumnHorizontalScrollView.setParam(getActivity(), mScreenWidth, mRadioGroup_content, shade_left, shade_right, ll_more_columns, rl_column);
		for(int i = 0; i< count; i++){
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth , LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
//			TextView localTextView = (TextView) mInflater.inflate(R.layout.column_radio_item, null);
			TextView columnTextView = new TextView(getActivity());
			columnTextView.setTextAppearance(getActivity(), R.style.top_category_scroll_view_item_text);
//			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
			columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
			columnTextView.setGravity(Gravity.CENTER);
			columnTextView.setPadding(5, 5, 5, 5);
			columnTextView.setId(i);
			columnTextView.setText(userChannelList.get(i).getName());
			columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
			if(columnSelectIndex == i){
				columnTextView.setSelected(true);
			}
			columnTextView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
			          for(int i = 0;i < mRadioGroup_content.getChildCount();i++){
				          View localView = mRadioGroup_content.getChildAt(i);
				          if (localView != v)
				        	  localView.setSelected(false);
				          else{
				        	  localView.setSelected(true);
				        	  mViewPager.setCurrentItem(i);
				          }
			          }
			          Toast.makeText(getActivity(), userChannelList.get(v.getId()).getName(), Toast.LENGTH_SHORT).show();
				}
			});
			mRadioGroup_content.addView(columnTextView, i ,params);
		}
	}
	/** 
	 *  选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		//判断是否选中
		for (int j = 0; j <  mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}
	/** 
	 *  初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();//清空
		int count =  userChannelList.size();
		for(int i = 0; i< count;i++){
			Bundle data = new Bundle();
			Log.i("data===","==="+data );
    		data.putString("text", userChannelList.get(i).getName());
    		data.putInt("id", userChannelList.get(i).getId());
			NewsFragment newfragment = new NewsFragment();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}
		NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
//		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
	}
	/** 
	 *  ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener= new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(position);
			selectTab(position);
		}
	};
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getActivity().getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	protected void initSlidingMenu() {
		side_drawer = new DrawerView(getActivity()).initSlidingMenu();
	}
	
	private long mExitTime;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case CHANNELREQUEST:
			if(resultCode == CHANNELRESULT){
				setChangelView();
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
