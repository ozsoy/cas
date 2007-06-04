/*
 * Copyright 2005 The JA-SIG Collaborative. All rights reserved. See license
 * distributed with this file and available online at
 * http://www.uportal.org/license.html
 */
package org.jasig.cas.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.jasig.cas.util.annotation.NotNull;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;

/**
 * Factory Bean for creating a private key from a file.
 * 
 * @author Scott Battaglia
 * @version $Revision: 1.1 $ $Date: 2005/08/19 18:27:17 $
 * @since 3.1
 *
 */
public final class DSAPrivateKeyFactoryBean extends AbstractFactoryBean {

    @NotNull
    private Resource location;

    @Override
    protected Object createInstance() throws Exception {
        final InputStream privKey = this.location.getInputStream();
        try {
            final byte[] bytes = new byte[privKey.available()];
            privKey.read(bytes);
            privKey.close();
            final PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance("DSA");
            return factory.generatePrivate(privSpec);
        } finally {
            privKey.close();
        }
    }

    public Class getObjectType() {
        return DSAPrivateKey.class;
    }

    public void setLocation(final Resource location) {
        this.location = location;
    }
}