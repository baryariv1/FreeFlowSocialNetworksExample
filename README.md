# FreeFlowSocialNetworksExample

# Comcast FreeFlow to create Android awesome custom layouts 

I have tried to reach a complex UI patterns for a long time, and I did a lot of great things with RecyclerView, finally, I found the perfect library for this stuff.
FreeFlow is a great library to create custom layouts with view recycling.
With FreeFlow i built interesting and complex layouts like the following example: 

![ScreenShot](https://github.com/baryariv1/FreeFlowSocialNetworksExample/blob/master/Photos/example.png)

You can download the library from https://github.com/Comcast/FreeFlow
  * Download the library.
  * Add the library to “libraries” folder:
  
  ![ScreenShot](https://github.com/baryariv1/FreeFlowSocialNetworksExample/blob/master/Photos/libraries.png)
  
  * Add in settings.gradle : 
  ```java
  include ':app', ':libraries:FreeFlow'
```
  * Add in “app” build.gradle :
  
    ```java
  dependencies {
    compile project(':libraries:FreeFlow')
}
```

Now for work:
I will show you how to create the layout from the example “FreeFlowSocialNetworksExample”.
First create new xml file under “layout” folder, this view contains ImageView where the social network image will show.

```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/rootView">

    <ImageView
        android:id="@+id/snImg"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"/>

</LinearLayout>
```
Create  new class - “SocialNetworkLayout”, in that class we define the layout settings.
SocialNetworkLayout should extend FreeFlowLayoutBase and implement FreeFlowLayout.
Override setDimensions : 

```java
    @Override
    public void setDimensions(int measuredWidth, int measuredHeight) {
        super.setDimensions(measuredWidth, measuredHeight);
        largeItemSize = ((2 * measuredHeight) - Utils.dpToPx(10)) / 3;
        regularItemSize = (measuredHeight - Utils.dpToPx(20)) / 3 ;
    }
```

We need to define the size of our items, in that case we define two items: 
  * largeItemSize – size of 2/3 of screen height.
  * regularItemSize – size of 1/3 of screen height.
  
Note that I reduce 10/20 dp for margin of 10dp between the items.
i’m using Utils.dpToPx() because i want to get a responsive layout and to keep same view on any type of device. 

  * Override prepareLayout – here we set each item dimensions and position:
  
```java
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
```

  * Define the map and get the section into variable.
  * Declare row and column indexes.
  * Loop over the section items:
      * Create FreeFlowItem and set his attributes, get data from section.
      * Create Rect to declare the item position.
  * When the item index is greater than 7 we see that the pattern becomes fixed and we want to increase the column index, if index % 3 equals to 1 (that means its new column).
  * For the row index we just check for index % 3 and set the index respectively.
  * Now for the interesting part – set the item position. I’m not going to cover what I did with all of the items, but it should be clear enough:
    
  Index 0:
    
      Set top position to 0.
      
      Set left position to 0. 
      
      Set bottom position to largeItemSize.
      
      Set right position to largeItemSize. 
      
      We start at the top-left corner and the size of the item is both width and height large item size.
        
  Index 1:           
      
      Set top position to largeItemSize + 10dp.
      
      Set left position to 0.
      
      Set bottom position to largeItemSize + regularItemSize + 10dp.
      
      Set right position to largeItemSize.
      
      From left we start at 0, from top we start at large item size(item index 0) + 10dp(margin top).
      
      The bottom of the item is item top + regular item size, item height is regular.
      
      The item width is large item size so item right is item left(0) + large item size.
          
  Items 2-6: Set the positions at the same way as items 0,1.
    
      For default items(index > 6):  Simple grid with 3 rows.
      
      If row index is 0 - top position is 0.
      
      If row index is 1 - top position is regular item size + 10dp.
      
      If row index is 2 – top position is large item size + 10dp.
      
      Bottom position is item top position + 10dp + regular item size.
        
      Left position is a little bit more complex but still pretty easy – 
	               Column index * regular item size + column index * 10dp.
	               
      Right position is item left position + regular item size.
          
  * Finally we set the Rect top item’s frame attribute and add the item & data to the map.

  * We can choose vertical or horizontal scrolling or both by returning true\false form those methods:

```java
    @Override
    public boolean verticalScrollEnabled() {
        return false;
    }

    @Override
    public boolean horizontalScrollEnabled(){
        return true;
    }
```

  * We have to override the next two methods and return the width\height of our layout, previously we decided to go with horizontal scrolling so we need to return the last item right position, vertical scrolling is disabled so we return the screen height:
  
  ```java
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
  ```
  
  Create new class“SocialNetworksAdapter”, this is the layout adapter:
    The adapter should implement SectionedAdapter.
    For smooth scrolling use ViewHolder Pattern, add inner class 	“SocialNetworksViewHolder”:
    
```java
    public class SocialNetworksViewHolder {
        private ImageView snImg;

        public SocialNetworksViewHolder(View convertView) {
            snImg = (ImageView) convertView.findViewById(R.id.snImg);
        }
    }
```

  SocialNetworksAdapter constructor, here we add socialNetwork item to the section for each item in socialNetworks list:
  
 ```java
 public SocialNetworksAdapter(Context ctx, List<Integer> socialNetworks) {
        this.ctx = ctx;
        this.socialNetworks = socialNetworks;
        section = new Section();
        section.setSectionTitle("Section ");

        for (int i = 0; i < socialNetworks.size(); i++) {
            section.addItem(socialNetworks.get(i));
        }
    }
```

  Override getItemView – here we inflate the layout if needed, set the view holder and set the view data:
  
  ```java
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
   ```
   
What we have to do now? Just create the view, layout, adapter and set all together.
In your View\Fragment\Activity add:

```java
  FreeFlowContainer socialNetworksView = new FreeFlowContainer(this);
        SocialNetworksLayout socialNetworksLayout = new SocialNetworksLayout();
        SocialNetworksAdapter socialNetworksAdapter = new SocialNetworksAdapter(this, snList);
        socialNetworksView.setLayout(socialNetworksLayout);
        socialNetworksView.setAdapter(socialNetworksAdapter);
        socialNetworksView.dataInvalidated(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        socialNetworksView.setLayoutParams(params);
        rootView.addView(socialNetworksView);
```
    
  We call dataInvalidate with a false parameter on socialNetworksView because we don’t want to recalculate the scroll positions, you can try to replace it to true and see what happens.	
    
  You can also create FreeFlowContainer via xml but I’ll not cover it here (it’s simple..).

With Comcast FreeFlow you can create your own layouts and they can be much more complex.

You can find the full project on GitHub:
	https://github.com/baryariv1/FreeFlowSocialNetworksExample

    
