/*
 * MIT License
 *
 * Copyright (c) 2017 K Sun <jcodeing@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.google.android.exoplayer2.util;

import android.text.TextUtils;

public class KDecoder {

    /**
     * k-encode-version
     */
    private String kev;
    /**
     * k-key byte[]
     */
    private byte[] kkey;

    private KDecoder(String kev, byte[] kkey) {
        this.kev = kev;
        this.kkey = kkey;
        //Init RC4K
        S = new int[256];
        rc4k_init(kkey, S);
    }

    /**
     * @param kei k-encode-info : kev(k-encode-version),kkid(k-key-id),kkey(k-key)
     * @return KDecoder [Parse failure return null]
     */
    public static KDecoder create(String kei) {
        try {
            String[] split = kei.split(",");
            String kev = split[0];
            String kkid = split[1];
            String kkey = split[2];

            String kkey_decrypt = null;
            // =========@private@=========
            // TODO decrypt... switch (kkid)
            // =========@private@=========

            if (!TextUtils.isEmpty(kev) && !TextUtils.isEmpty(kkey_decrypt))
                return new KDecoder(kev, kkey_decrypt.getBytes());
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * Origin file decode offset (Used to jump over do not need to decode)
     */
    private int decodeOffset;

    public KDecoder decodeOffset(int decodeOffset) {
        this.decodeOffset = decodeOffset;
        return this;
    }

    /**
     * decode buffer[offset~decodeLength]
     *
     * @param buffer       Need to decode the buffer
     * @param offset       Decode offset
     * @param decodeLength Decode length
     * @param filePointer  Origin file start filePointer
     */
    public void decode(byte[] buffer, int offset, int decodeLength, int filePointer) {
        if (!TextUtils.isEmpty(kev) && S != null) {
            // =========@private@=========
            // TODO decode... switch (kkey)
            // =========@private@=========
        }
    }


    // ============================@RC4K Algorithm@============================
    private int[] S;

    /**
     * @param key key[1<=len<=256] len, typically between 5 and 16
     * @param S   S[256]
     */
    private void rc4k_init(byte[] key, int[] S) {
        int len = key.length;
        int i, j = 0;
        //Init S
        for (i = 0; i < 256; i++)
            S[i] = i;
        //KSA
        for (i = 0; i < 256; i++) {//& 0xff:byte->int:(-1)->255
            j = (j + S[i] + (key[i % len] & 0xff)) % 256;
            S[i] = S[i] ^ S[j];
            S[j] = S[i] ^ S[j];
            S[i] = S[i] ^ S[j];
        }
    }
}
