package com.freeflowsocialnetworksexample;

import android.graphics.Rect;

import com.comcast.freeflow.core.FreeFlowItem;
import com.comcast.freeflow.core.Section;
import com.comcast.freeflow.layouts.FreeFlowLayout;
import com.comcast.freeflow.layouts.FreeFlowLayoutBase;
import com.comcast.freeflow.utils.ViewUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bar yariv on 11/01/2016.
 */
public class SocialNetworksLayout extends FreeFlowLayoutBase implements FreeFlowLayout {

    private static final String TAG = "ProfileImagesAlbumLayout";

    private int largeItemSize;
    private int regularItemSize;
    private HashMap<Object, FreeFlowItem> map;
    private Section s;


    @Override
    public void setDimensions(int measuredWidth, int measuredHeight) {
        super.setDimensions(measuredWidth, measuredHeight);
        largeItemSize = ((2 * measuredHeight) - Utils.dpToPx(10)) / 3;
        regularItemSize = (measuredHeight - Utils.dpToPx(20)) / 3 ;
    }

    @Override
    public void prepareLayout(){
        map = new HashMap<>();
        s = itemsAdapter.getSection(0);
        int rowIndex = 0;
        int colIndex = 0;
        for (int i = 0; i < s.getDataCount(); i++) {
            FreeFlowItem item = new FreeFlowItem();
            item.isHeader = false;
            item.itemIndex = i;
            item.itemSection = 0;
            item.data = s.getDataAtIndex(i);
            Rect r = new Rect();

            if(i > 7 && i % 3 == 1)
                colIndex++;

            switch(i % 3){
                case 1:
                    rowIndex = 0;
                    break;
                case 2:
                    rowIndex = 1;
                    break;
                case 0:
                    rowIndex = 2;
                    break;
            }

            switch (i) {
                case (0):
                        r.top = 0;
                        r.left = 0;
                        r.bottom = largeItemSize;
                        r.right = largeItemSize;
                    break;
                case (1):
                        r.top = largeItemSize + Utils.dpToPx(10);
                        r.left = 0;
                        r.bottom = largeItemSize + regularItemSize + Utils.dpToPx(10);
                        r.right = largeItemSize;
                    break;
                case (2):
                        r.top = 0;
                        r.left = largeItemSize + Utils.dpToPx(10);
                        r.bottom = regularItemSize;
                        r.right = r.left + largeItemSize;
                    break;
                case (3):
                    r.top = regularItemSize + Utils.dpToPx(10);
                    r.left = largeItemSize + Utils.dpToPx(10);
                    r.bottom =  r.top + regularItemSize;
                    r.right = r.left + regularItemSize;
                    break;
                case (4):
                    r.top = largeItemSize + Utils.dpToPx(10);
                    r.left = largeItemSize + Utils.dpToPx(10);
                    r.bottom =  r.top + regularItemSize;
                    r.right = r.left + regularItemSize;
                    break;
                case (5):
                    r.top = regularItemSize + Utils.dpToPx(10);
                    r.left = largeItemSize + regularItemSize + Utils.dpToPx(20);
                    r.bottom =  r.top + regularItemSize;
                    r.right = r.left + regularItemSize;
                    break;
                case (6):
                    r.top = regularItemSize + regularItemSize + Utils.dpToPx(20);
                    r.left = largeItemSize + regularItemSize + Utils.dpToPx(20);
                    r.bottom =  r.top + regularItemSize;
                    r.right = r.left + regularItemSize;
                    colIndex = 4;
                    break;
                default:
                    if(rowIndex == 2)
                        r.top = largeItemSize + Utils.dpToPx(10);
                    else if (rowIndex == 0)
                        r.top = rowIndex * regularItemSize;
                    else
                        r.top = rowIndex * regularItemSize + Utils.dpToPx(10);
                    r.bottom = r.top + regularItemSize;
                    r.left = colIndex * regularItemSize + Utils.dpToPx(colIndex * 10);
                    r.right = r.left + regularItemSize;
                    break;
            }
            item.frame = r;
            map.put(s.getDataAtIndex(i), item);
        }
    }

    @Override
    public HashMap<Object, FreeFlowItem> getItemProxies(
            int viewPortLeft, int viewPortTop) {

        Rect viewport = new Rect(viewPortLeft,
                viewPortTop,
                viewPortLeft + width,
                viewPortTop + height);

        HashMap<Object, FreeFlowItem> ret = new HashMap<>();

        Iterator<Map.Entry<Object, FreeFlowItem>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, FreeFlowItem> pairs = it.next();
            FreeFlowItem p = pairs.getValue();
            if ( Rect.intersects(p.frame, viewport) ) {
                ret.put(pairs.getKey(), p);
            }
        }
        return ret;
    }

    @Override
    public FreeFlowItem getFreeFlowItemForItem(Object item) {
        return map.get(item);
    }

    @Override
    public int getContentWidth() {
        Object lastFrameData = s.getDataAtIndex(s.getDataCount() - 1);
        FreeFlowItem fd = map.get(lastFrameData);
        return  fd.frame.right;
    }

    @Override
    public int getContentHeight() {
        return height;
    }

    @Override
    public FreeFlowItem getItemAt(float x, float y) {
        return ViewUtils.getItemAt(map, (int) x, (int) y);
    }

    @Override
    public void setLayoutParams(FreeFlowLayoutParams params) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean verticalScrollEnabled() {
        return false;
    }

    @Override
    public boolean horizontalScrollEnabled(){
        return true;
    }
}


