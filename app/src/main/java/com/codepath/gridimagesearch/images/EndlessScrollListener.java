package com.codepath.gridimagesearch.images;


import android.widget.AbsListView;

public abstract class EndlessScrollListener implements AbsListView.OnScrollListener{

    private final static String TAG = "EndlessScrollListener";

    // Min items to have below cur position before loading more
    private int visibleThreshold = 5;
    // current offset index
    private int currentPage = 0;
    // Total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // true if we are still waiting for the last set of data to load
    private boolean loading = true;
    // Starting page index
    private int startingPageIndex = 0;

    public EndlessScrollListener() {

    }

    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    /**
     * From: http://developer.android.com/reference/android/widget/AbsListView.OnScrollListener.html#onScroll(android.widget.AbsListView, int, int, int)
     * @param view - View The view who's scroll state is being reported
     * @param firstVisibleItem int the index of the first visible cell (ignore if visibleItemCount == 0)
     * @param visibleItemCount int the number of visible cells
     * @param totalItemCount int the number of items in the list adapter
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        /**
         * If the total item count is 0 and the previous isn't, assume the list is invalid and
         * reset back to initial state
         *
         * Note: This seems like a weird state. Isn't it always an error when totalItemCount is less
         * than the previous total item count?
         */
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;

            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        /**
         * If still loading, check if the dataset count has changed. If so, then it has finished
         * loading and the current page and total item count can be updated.
         */
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        /**
         * If not currently loading, check if we have breached the visibleThreshold to reload more
         * data. If so, execute onLoadMore and fetch the data
         *
         * reordered to make more sense:
         */
        if (!loading && (firstVisibleItem + visibleThreshold + visibleItemCount >= totalItemCount)) {

            // can only load pages 0 - 7 due to the limitation of the Google Images API
            if (currentPage < 7){
                onLoadMore(currentPage + 1);
                loading = true;
            }

        }
    }

    /**
     * Defines the process for actually loading more data based on page
     * @param page int
     */
    public abstract void onLoadMore(int page);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // No need to do anything
    }
}
