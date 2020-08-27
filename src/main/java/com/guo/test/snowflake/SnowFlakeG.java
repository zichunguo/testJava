package com.guo.test.snowflake;

import java.util.Date;

/**
 * 我的改进 snowflake 算法 -- java实现
 * 对时间回退情况进行处理
 * 
 * @author chun
 * @date 2020/03/09
 */
public class SnowFlakeG {

    /** 起始的时间戳 (2020-01-01) */
	private final static long START_STMP = 1577808000000L;

    // 每一部分占用的位数
    /** 数据中心占用的位数 */
    private final static long DATACENTER_BIT = 5;
    /** 机器标识占用的位数 */
    private final static long MACHINE_BIT = 5;
    /** 序列号占用的位数 */
    private final static long SEQUENCE_BIT = 12;

    //每一部分的最大值
    /** 数据中心最大值，31 */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    /** 机器标识最大值，31 */
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    /** 序列号最大值，4095 (0b111111111111=0xfff=4095) */
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    //每一部分向左的位移
    /** 机器ID向左移位数，12位 */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    /** 向左移位数，17位(12+5) */
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    /** 时间截向左移动位数，22位(12+5+5) */
    private final static long TIMESTMP_LEFT = SEQUENCE_BIT + MACHINE_BIT + DATACENTER_BIT;
    
    // 变量定义
    /** 数据中心ID(0~31) */
    private long datacenterId;  //数据中心
    /** 机器标识ID(0~31) */
    private long machineId;     //机器标识
    /** 毫秒内序列(0~4095) */
    private long sequence = 0L; //序列号
    /** 上次生成ID的时间截 */
    private long lastStmp = -1L;//上一次时间戳
//    private long lastStmp = 1577808000001L;//上一次时间戳，用于测试
    /** 时钟是否回退的标志 */
    private boolean isClockBack = false;// 时钟是否回退的标志

    /**
     * 构造函数
     * @param datacenterId 数据中心ID (0~31)
     * @param machineId 机器标识ID(0~31)
     */
    public SnowFlakeG(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID，(该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }
    
//    long time = 1577808000000L;
    /**
     * 产生下一个ID，自定义的方法，该方法解决时钟回退问题(该方法是线程安全的)
     * @return
     */
    public synchronized long nextIdG() {
    	long currStmp = getNewstmp();
//    	lastStmp = 1577808000001L;
//    	long currStmp = time;
    	//如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过
    	if (currStmp < lastStmp) {
    		currStmp = lastStmp;
    		isClockBack = true;
//            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else {
        	isClockBack = false;
        }
//    	if (4095 == sequence) {
//			System.out.println(sequence);
//			time = 1577808000003L;
//		}

    	//如果是同一时间生成的，则进行毫秒内序列
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
            	if (isClockBack) {
            		// 当时间回退时，同一毫秒的序列数已经达到最大就要手动的加 1 毫秒
            		currStmp = (lastStmp + 1);
				} else {
					// 阻塞到下一个毫秒,获得新的时间戳
					currStmp = getNextMill();
				}
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        // 修改最后生成 id 的时间戳
        lastStmp = currStmp;
        //移位并通过或运算拼到一起组成64位的ID
    	return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    /**
     * 返回以毫秒为单位的当前时间
     * @return 当前时间(毫秒)
     */
    private long getNewstmp() {
        return System.currentTimeMillis();
    }
    
    /**
     * 获取 id 对应的时间戳
     * @param id id 值
     * @return
     */
    public Long getTimeStamp(Long id) {
    	return (id >> TIMESTMP_LEFT) + START_STMP;
//    	return (id >> TIMESTMP_LEFT & ~(-1L << 41L)) + START_STMP;
    }
    
    /**
     * 获取 id 对应的数据中心的值
     * @param id
     * @return
     */
    public Long getDataCenterId(Long id) {
//    	return (id >> DATACENTER_LEFT) & MAX_DATACENTER_NUM;
    	return id >> DATACENTER_LEFT & ~(-1L << DATACENTER_BIT);
    }
    
    /**
     * 获取 id 对应的工作机器的值
     * @param id
     * @return
     */
    public Long getWorkerId(Long id) {
//    	return (id >> MACHINE_LEFT) & MAX_MACHINE_NUM;
    	return id >> MACHINE_LEFT & ~(-1L << MACHINE_BIT);
    }
    
    /**
     * 获取 id 对应的毫秒内序列值
     * @param id
     * @return
     */
    public Long getSequence(Long id) {
    	return id & MAX_SEQUENCE;
    }

    public static void main(String[] args) {
        SnowFlakeG snowFlake = new SnowFlakeG(0, 0);

        for (int i = 0; i < (1 << 12) + 2; i++) {
        	long id = snowFlake.nextIdG();
        	System.out.println(Long.toBinaryString(id));
            System.out.println(id);
            System.out.println(new Date(snowFlake.getTimeStamp(id)));
            System.out.println(snowFlake.getDataCenterId(id));
            System.out.println(snowFlake.getWorkerId(id));
            System.out.println(snowFlake.getSequence(id));
        }

    }
}