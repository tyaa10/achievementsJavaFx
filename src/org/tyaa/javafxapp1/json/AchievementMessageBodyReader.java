/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tyaa.javafxapp1.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.END_OBJECT;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.START_OBJECT;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.tyaa.javafxapp1.entity.Achievement;

/**
 *
 * @author yurii
 */
@Provider
@Consumes({"application/json"})
public class AchievementMessageBodyReader implements MessageBodyReader<List<Achievement>> {

    @Override
    public boolean isReadable(Class<?> type, java.lang.reflect.Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }

    @Override
    public List<Achievement> readFrom(Class<List<Achievement>> type, java.lang.reflect.Type type1, Annotation[] antns, MediaType mt, MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        
        if (mt.getType().equals("application") && mt.getSubtype().equals("json")) {
            Achievement achievement = new Achievement();
            List<Achievement> achievements = new ArrayList();
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()) {
              JsonParser.Event event = parser.next();
              switch (event) {
                case START_OBJECT:
                  achievement = new Achievement();
                  break;
                case END_OBJECT:
                  achievements.add(achievement);
                  break;
                case KEY_NAME:
                  String key = parser.getString();
                  parser.next();
                  switch (key) {
                    case "id":
                      achievement.setId(parser.getLong());
                      break;
                    case "title":
                      achievement.setTitle(parser.getString());
                      break;
                    case "content":
                      achievement.setContent(parser.getString());
                      break; 
                    default:
                      break;
                  }
                  break;
                default:
                  break;
              }
            }
            return achievements;
        }
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }
}
