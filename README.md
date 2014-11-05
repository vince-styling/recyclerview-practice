RecyclerView Practice
=====================

This project contained my RecyclerView practicing code. I've heard this widget after Android Lollipop released. I thought it can be benefit for a task which happened in my past work. So I paid much attention in this, and reworking that effect. Below is what I'm got.

![Runtime Screenshot](screenshot.png "screenshot")

Throughout this process, first I've tried to achieving the game video list showing by nesting RecyclerViews. But it's hard to making their height stretching dynamically depends on how much items it have. Then I started a [question](http://stackoverflow.com/questions/26717928/is-there-a-way-to-share-a-same-layoutmanager-between-multiple-nested-recyclervie) in stackoverflow to looking for a good way.

Given that video items are completely same structure, I also want those nesting RecyclerViews can share their views. But I can't make sure that they does. Thus I used GridLayout instead of the nested RecyclerView, and recycling views which inside those GridLayout myself.

So far it worked. But to be honest, that isn't a good implementation in productization context. I'm still looking for a good way to achieving how to share them.

As records, there are two blog posts I'm referenced to study RecyclerView, [Building a RecyclerView LayoutManager series](http://wiresareobsolete.com/2014/09/building-a-recyclerview-layoutmanager-part-1/) and [A First Glance at Android’s RecyclerView](http://www.grokkingandroid.com/first-glance-androids-recyclerview/).