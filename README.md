双层Toolbar上拉隐藏实现
===
实现效果
---
![demo](https://github.com/axlecho/DoubleToolbarHideAnimation/raw/master/demo.gif)

实现过程
---

####第一层Toolbar的隐藏
使用的是AppBarLayout的layout_scrollFlags属性及layout_behavior属性的配合
```xml
    // ---> activity_main.xml
	<android.support.design.widget.AppBarLayout
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		app:elevation="0dp"
		app:layout_scrollFlags="scroll|enterAlways">
			...
		</android.support.design.widget.TabLayout>
	</android.support.design.widget.AppBarLayout>
	
	<FrameLayout
		android:id="@+id/content_layout"
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		app:layout_behavior="@string/appbar_scrolling_view_behavior" />
```

####第二层Toolbar的隐藏
通过OnScrollListener来实现，上拉到一定高度，给第二层Toolbar添加属性动画隐藏掉
这个有几个要注意到地方
1.第二层Toolbar是叠在listiew上的，listview通过paddingTop留下空间，因此第二层Toolbar需要指定高度
2.设置`android:clipToPadding="false"`，上拉时listview才会覆盖padding的空间，不然隐藏toolbar后会留下一层空白
```
    <android.support.v7.widget.RecyclerView
        android:id="@+id/DataListView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:paddingTop="@dimen/second_navigation_bar_height"
        android:scrollbars="none" />
```

然后是滚动监听,(这部分代码是网上，出处忘记了,不过大体都是这样实现的)
```java
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > hideThreshold && controlsVisible) {//移动总距离大于规定距离 并且是显示状态就隐藏
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;//归零
        } else if (scrolledDistance < -hideThreshold && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) { //显示状态向上滑动 或 隐藏状态向下滑动 总距离增加
            scrolledDistance += dy;
        }
    }
```

然后是属性动画的实现,这里的secondNavigationBarHeight是通过geViewTreeObserver来获取的，防止view未创建时滑动，引起奔溃，正常情况下不会发生，直接使用`@dimen/second_navigation_bar_height`也可以
```
    private void hideSeconNavigationBar() {
        if (secondNavigationBarHeight == 0) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(secondNavigationBar, View.TRANSLATION_Y, 0, -secondNavigationBarHeight);
        animator.setDuration(ANIMATOR_SPEED);
        animator.start();
    }
	
	private void showSeconNavigationBar() {
        if (secondNavigationBarHeight == 0) {
            return;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(secondNavigationBar, View.TRANSLATION_Y, -secondNavigationBarHeight, 0);
        animator.setDuration(ANIMATOR_SPEED);
        animator.start();
    }
```


