package org.rural.config;

import org.rural.interceptor.Interceptor;

import java.util.*;

/**
 * Created by yuantao on 2015/3/15.
 */
public class InterceptorConfigHolder {
    private List<InterceptorConfig> interceptorConfigs;

    public InterceptorConfigHolder() {
        this.interceptorConfigs = new ArrayList<InterceptorConfig>();
    }

    public void add(String path, Class<? extends Interceptor> clazz) {
        this.interceptorConfigs.add(new InterceptorConfig(path, clazz));
    }

    public List<InterceptorConfig> getInterceptorConfigs() {
        return this.interceptorConfigs;
    }

    public static class InterceptorConfig {
        private String path;
        private Class<? extends Interceptor> clazz;

        private InterceptorConfig(String path, Class<? extends Interceptor> clazz) {
            this.path = path;
            this.clazz = clazz;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Class<? extends Interceptor> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends Interceptor> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InterceptorConfig that = (InterceptorConfig) o;

            if (!clazz.equals(that.clazz)) return false;
            if (!path.equals(that.path)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = path.hashCode();
            result = 31 * result + clazz.hashCode();
            return result;
        }
    }

}
