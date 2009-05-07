/*
 * Copyright 2009 Rafal Myslek <rafal.myslek@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *     
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.     
 */
package com.myslek.ragnarok.web.common;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public final class FacesUtils {

    public static int getRequestParamAsInt(FacesContext ctx, String name) {
        int ret = -1;
        try {
            ret = Integer.parseInt(FacesUtils.getRequestParam(ctx, name));
        } catch (NumberFormatException e) {
            // ignored
        }
        return ret;
    }

    public static <T extends Enum<T>> T getRequestParamAsEnum(FacesContext ctx, Class<T> enumType,
            String name) {
        T ret = null;
        try {
            ret = Enum.valueOf(enumType, getRequestParam(ctx, name));
        } catch (IllegalArgumentException e) {
            // ignored
        }
        return ret;
    }

    public static String getRequestParam(FacesContext ctx, String name) {
        Map<String, String> params = ctx.getExternalContext().getRequestParameterMap();
        return params.get(name);
    }
    
    public static Object getSessionAttribute(FacesContext ctx, String name) {
        HttpSession httpSession = (HttpSession) ctx.getExternalContext().getSession(false);
        if (httpSession != null) {
            return httpSession.getAttribute(name);
        }
        return null;
    }
}
