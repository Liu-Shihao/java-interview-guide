package com.lsh.java;


import org.openjdk.jol.info.ClassLayout;

public class Class02_SynchronizedUpgrade {

    private static final Object lock = new Object();

    /**
     * Synchronized升级过程
     * @param args
     *
     * <dependency>
     *     <groupId>org.openjdk.jol</groupId>
     *     <artifactId>jol-core</artifactId>
     *     <version>0.16</version>
     * </dependency>
     */
    public static void main(String[] args) {
        // 第一阶段：无锁
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        // 第二阶段：偏向锁
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        // 第三阶段：轻量级锁
        new Thread(() -> {
            synchronized (lock) {
                System.out.println(ClassLayout.parseInstance(lock).toPrintable());
            }
        }).start();

        // 第四阶段：重量级锁
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    System.out.println(ClassLayout.parseInstance(lock).toPrintable());
                }
            }).start();
        }
        /**
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         *
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x000000016fe0ab00 (thin lock: 0x000000016fe0ab00)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         *
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000000172646960 (thin lock: 0x0000000172646960)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         *
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000600001a341a2 (fat lock: 0x0000600001a341a2)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         *
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000600001a341a2 (fat lock: 0x0000600001a341a2)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         *
         * java.lang.Object object internals:
         * OFF  SZ   TYPE DESCRIPTION               VALUE
         *   0   8        (object header: mark)     0x0000600001a341a2 (fat lock: 0x0000600001a341a2)
         *   8   4        (object header: class)    0x00000d68
         *  12   4        (object alignment gap)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */
    }
}
