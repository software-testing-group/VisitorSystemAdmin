package beike.visitorsystem.utils;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateID {

    // ==============================Fields===========================================
    /** ��ʼʱ��� (2015-01-01) */
    private final long twepoch = 1420041600000L;

    /** ����id��ռ��λ�� */
    private final long workerIdBits = 4L;  //5

    /** ���ݱ�ʶid��ռ��λ�� */
    private final long datacenterIdBits = 0L;   //5

    /** ֧�ֵ�������id�������31 (�����λ�㷨���Ժܿ�ļ������λ�����������ܱ�ʾ�����ʮ������) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** ֧�ֵ�������ݱ�ʶid�������31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** ������id��ռ��λ�� */
    private final long sequenceBits = 5L;   //12

    /** ����ID������12λ */
    private final long workerIdShift = sequenceBits;

    /** ���ݱ�ʶid������17λ(12+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** ʱ���������22λ(5+5+12) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** �������е����룬����Ϊ4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** ��������ID(0~31) */
    private long workerId;

    /** ��������ID(0~31) */
    private long datacenterId;

    /** ����������(0~4095) */
    private long sequence = 0L;

    /** �ϴ�����ID��ʱ��� */
    private long lastTimestamp = -1L;
    
    //==============================Constructors=====================================
    /**
     * ���캯��
     * @param workerId ����ID (0~31)
     * @param datacenterId ��������ID (0~31)
     */
    public void setGenerateID(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    
 // ==============================Methods==========================================
    /**
     * �����һ��ID (�÷������̰߳�ȫ��)
     * @return SnowflakeId
     */
    protected synchronized long nextId() {
        long timestamp = timeGen();

        //�����ǰʱ��С����һ��ID���ɵ�ʱ�����˵��ϵͳʱ�ӻ��˹����ʱ��Ӧ���׳��쳣
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //�����ͬһʱ�����ɵģ�����к���������
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //�������������
            if (sequence == 0) {
                //��������һ������,����µ�ʱ���
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //ʱ����ı䣬��������������
        else {
            sequence = 0L;
        }

        //�ϴ�����ID��ʱ���
        lastTimestamp = timestamp;

        //��λ��ͨ��������ƴ��һ�����64λ��ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * ��������һ�����룬ֱ������µ�ʱ���
     * @param lastTimestamp �ϴ�����ID��ʱ���
     * @return ��ǰʱ���
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * �����Ժ���Ϊ��λ�ĵ�ǰʱ��
     * @return ��ǰʱ��(����)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
    
    public BigInteger getID()
    {
    	Random random = new Random();
		int workId = random.nextInt(15);
		int centerId = 0;
		
		GenerateID getId = new GenerateID();
		getId.setGenerateID(workId, centerId);
		return new BigInteger(getId.nextId() + "");
    }
    
	public static void main(String[] args)
	{
		GenerateID getId = new GenerateID();
		System.out.println(getId.getID());
	}
}