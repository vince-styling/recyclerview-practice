package com.vincestyling.recyclerview_practice;

import com.duowan.mobile.netroid.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vincestyling.recyclerview_practice.entity.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameListRequest extends Request<List<Data>> {
    public GameListRequest(String url, Listener<List<Data>> listener) {
        super(url, listener);
        setCacheExpireTime(TimeUnit.MINUTES, 20);
    }

    @Override
    protected Response<List<Data>> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, response.charset);

            LinkedList<Game> gameList = new Gson().fromJson(
                    json, new TypeToken<LinkedList<Game>>(){}.getType());

            if (gameList == null || gameList.size() == 0)
                return Response.error(new NetroidError(json));

            Collections.shuffle(gameList);
            List<Data> dataList = new ArrayList<Data>(gameList.size() + 2);

            int myGameCount = randomInt(5);
            if (myGameCount == 0) {
                dataList.add(new WithoutGameData());
            } else {
                dataList.add(new MyGameTitleData());
                for (int i = 0; i < myGameCount; i++) {
                    dataList.add(new GameData(gameList.pop()));
                }
            }

            dataList.add(new FavoriteGameTitleData());
            for (Game game : gameList) {
                dataList.add(new GameData(game));
                game.setFavorite(true);
            }

            return Response.success(dataList, response);
        } catch (Exception e) {
            return Response.error(new NetroidError(e));
        }
    }

    public static long last_seed = 0;
    public static Random rand = new Random();
    private static int randomInt(int range) {
        if (range <= 0) return 0;
        long seed = System.currentTimeMillis();
        if (seed != last_seed) {
            last_seed = seed;
            rand.setSeed(seed);
        }
        return rand.nextInt(range);
    }
}
