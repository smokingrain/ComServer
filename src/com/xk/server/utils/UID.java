package com.xk.server.utils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;

public final class UID implements Serializable {
	private static int hostUnique;
	private static boolean hostUniqueSet = false;
	private static final Object lock = new Object();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = -32768;
	private static final long serialVersionUID = 1086053664494604050L;
	private final int unique;
	private final long time;
	private final short count;

	public UID() {
		synchronized (lock) {
			if (!(hostUniqueSet)) {
				hostUnique = new SecureRandom().nextInt();
				hostUniqueSet = true;
			}
			this.unique = hostUnique;
			if (lastCount == 32767) {
				boolean flag = Thread.interrupted();
				boolean flag1 = false;
				while (!(flag1)) {
					long l = System.currentTimeMillis();
					if (l == lastTime) {
						try {
							Thread.sleep(1L);
						} catch (InterruptedException interruptedexception) {
							flag = true;
						}
					} else {
						lastTime = (l >= lastTime) ? l : lastTime + 1L;
						lastCount = -32768;
						flag1 = true;
					}
				}
				if (flag)
					Thread.currentThread().interrupt();
			}
			this.time = lastTime;
			short tmp141_138 = lastCount;
			lastCount = (short) (tmp141_138 + 1);
			this.count = tmp141_138;
		}
	}

	public UID(short word0) {
		this.unique = 0;
		this.time = 0L;
		this.count = word0;
	}

	private UID(int i, long l, short word0) {
		this.unique = i;
		this.time = l;
		this.count = word0;
	}

	public int hashCode() {
		return ((int) this.time + this.count);
	}

	public boolean equals(Object obj) {
		if (obj instanceof UID) {
			UID uid = (UID) obj;
			return ((this.unique == uid.unique) && (this.count == uid.count) && (this.time == uid.time));
		}

		return false;
	}

	public String toString() {
		return Integer.toString(this.unique, 16) + ":"
				+ Long.toString(this.time, 16) + ":"
				+ Integer.toString(this.count, 16);
	}

	public void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeInt(this.unique);
		dataoutput.writeLong(this.time);
		dataoutput.writeShort(this.count);
	}

	public static UID read(DataInput datainput) throws IOException {
		int i = datainput.readInt();
		long l = datainput.readLong();
		short word0 = datainput.readShort();
		return new UID(i, l, word0);
	}
}