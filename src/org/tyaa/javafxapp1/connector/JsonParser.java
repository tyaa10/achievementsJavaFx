/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1.connector;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tyaa.javafxapp1.entity.Achievement;

/**
 *
 * @author yurii
 */
public class JsonParser {

    public List<Achievement> parseAchievements(String _jsonString) {

        JSONObject dataJsonObj = null;
        Achievement achievement = null;
        List<Achievement> achievements = new ArrayList<>();

        try {
            
            dataJsonObj = new JSONObject(_jsonString);
            JSONArray response = dataJsonObj.getJSONArray("data");
            
            JSONObject achievementJSONObject = null;
            
            for (int i = 0; i < response.length(); i++) {
                
                achievement = new Achievement();
                achievementJSONObject = response.getJSONObject(i);
                if(achievementJSONObject.has("id")){
                    achievement.setId(achievementJSONObject.getLong("id"));
                }
                if(achievementJSONObject.has("title")){
                    achievement.setTitle(achievementJSONObject.getString("title"));
                }
                if(achievementJSONObject.has("content")){
                    achievement.setContent(achievementJSONObject.getString("content"));
                }
                achievements.add(achievement);
            }            
        } catch (JSONException e) {
            e.printStackTrace();
            //System.err.println("There are some bad symbols in the JSON data");
        }

        return achievements;
    }
}
