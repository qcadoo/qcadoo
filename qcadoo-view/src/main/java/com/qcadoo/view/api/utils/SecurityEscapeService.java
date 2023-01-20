package com.qcadoo.view.api.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityEscapeService {

    public String encodeHtml(final String string) {
        return ESAPI.encoder().encodeForHTML(string);
    }

    public String decodeHtml(final String string) {
        return ESAPI.encoder().decodeForHTML(string);
    }

    @SuppressWarnings("unused")
    private boolean escapeMap(final Map<String, Object> map) {
        return escapeMap(map, false);
    }

    //@SuppressWarnings("unused")
    private boolean escapeMap(final Map<String, Object> map, final boolean isContentPrev) {
        boolean isContent = isContentPrev;

        boolean changed = false;

        java.util.Iterator<String> keys = map.keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();

            if (!isKeyIgnored(key)) {
                if ("content".equals(key)) {
                    isContent = true;
                }

                if (map.get(key) instanceof Map) {
                    try {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> subMap = (Map<String, Object>) map.get(key);

                        escapeMap(subMap, isContent);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                } else if (map.get(key) instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) map.get(key);

                    if (escapeJSON(jsonObject, isContent)) {
                        map.put(key, jsonObject);

                        changed = true;
                    }
                } else {
                    if (isContent) {
                        if (map.get(key) instanceof String) {
                            String value = (String) map.get(key);
                            String escapedValue = encodeHtml(value);

                            if (!escapedValue.equals(value)) {
                                map.put(key, escapedValue);

                                changed = true;
                            }
                        }
                    }
                }

                if (!isContentPrev && isContent) {
                    isContent = false;
                }
            }
        }

        return changed;
    }

    @SuppressWarnings("unused")
    private boolean escapeJSON(final JSONObject jsonObject) {
        return escapeJSON(jsonObject, false);
    }

    //@SuppressWarnings("unused")
    private boolean escapeJSON(final JSONObject jsonObject, final boolean isContentPrev) {
        boolean isContent = isContentPrev;

        boolean changed = false;

        try {
            java.util.Iterator<?> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();

                if (!isKeyIgnored(key)) {
                    if ("content".equals(key)) {
                        isContent = true;
                    }

                    if (jsonObject.get(key) instanceof JSONObject) {
                        JSONObject subJsonObject = (JSONObject) jsonObject.get(key);

                        escapeJSON(subJsonObject, isContent);
                    } else if (jsonObject.get(key) instanceof org.json.JSONArray) {
                        org.json.JSONArray subJsonObject = (org.json.JSONArray) jsonObject.get(key);

                        for (int i = 0; i < subJsonObject.length(); i++) {
                            if (subJsonObject.get(i) instanceof JSONObject) {
                                escapeJSON(subJsonObject.getJSONObject(i), isContent);
                            }
                        }
                    } else {
                        if (isContent) {
                            if (jsonObject.get(key) instanceof String) {
                                String value = (String) jsonObject.get(key);
                                String escapedValue = encodeHtml(value);

                                if (!escapedValue.equals(value)) {
                                    jsonObject.put(key, escapedValue);

                                    changed = true;
                                }
                            }
                        }
                    }

                    if (!isContentPrev && isContent) {
                        isContent = false;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return changed;
    }

    //@SuppressWarnings("unused")
    private boolean isKeyIgnored(final String key) {
        boolean result = false;

        //until list of ignored key is no more than 20-30 keys it is faster to use simple string comparison
        //but if there will be more keys than it is better to use TreeSet (treeSet.contains(key))
        if ("label".equals(key) || "filters".equals(key)) {
            result = true;
        }

        return result;
    }

    //this is alternative method for escaping HTML - faster but less secure
    //at this time this method is not used
    @SuppressWarnings("unused")
    private String myEscapeHtml(final String string) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);

            String sub;

            switch (c) {
                case '&':
                    sub = preventUnnecessaryEscaping(string, i, "&amp;");

                    break;

                case '<':
                    sub = "&lt;";

                    break;

                case '>':
                    sub = "&gt;";

                    break;

                case '"':
                    sub = "&quot;";

                    break;

                case '\'':
                    sub = "&#39;";

                    break;

                default:
                    sub = String.valueOf(c);

                    break;
            }

            result.append(sub);
        }

        return result.toString();
    }

    //@SuppressWarnings("unused")
    private String preventUnnecessaryEscaping(final String string, final int i, final String escapedString) {
        if (i + 4 < string.length()) {
            String sub = string.substring(i, i + 4);

            if ("&amp".equals(sub) || "&lt;".equals(sub) || "&gt;".equals(sub) || "&quo".equals(sub) || "&#39".equals(sub)) {
                //character is already escaped - don't escape it again
                return sub.substring(0, 1);
            } else {
                //character is not escaped - escape it
                return escapedString;
            }
        } else {
            //the rest of string is to small so for sure character is not escaped - escape it
            return escapedString;
        }
    }

}


