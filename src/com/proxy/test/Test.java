package com.proxy.test;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import com.proxy.domain.ServerConfig;

/**
 * 本地测试代理
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		ServerConfig config=new ServerConfig();
			config.setHost("127.0.0.1");
			config.setPort(1666);
		LocalManagerTest.startLocalService(config);

//		
//		ConnectorThreadTest connectorThreadTest=new ConnectorThreadTest(socket, forward, onBrokenCallBackInconnctor);
//		connectorThreadTest.start();
//		String[] ids = TimeZone.getAvailableIDs();
//		for (String id : ids) {
//			System.out.println(id);
//		}

//		Calendar ca1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//		long currentMiles = System.currentTimeMillis();
//
//		int after = Integer.parseInt(String.valueOf(currentMiles).substring(0, 6));
//		System.out.println(currentMiles);
//		System.out.println(after);

//		Calendar.getInstance()

//		System.currentTimeMillis();
//		
//
//		DataFilter dataFilter = new DataFilter(true);
//
//		String filter = dataFilter.getStringInChannel();
//
//		System.out.println("压缩前长度:" + filter.length());
//
//		String afteren = ZipUtil.compress(filter);
//
//		System.out.println("压缩后长度:" + afteren.length());
//
//		DataFilter df = DataFilter.createFromBase64(ZipUtil.uncompress(afteren));
//		
//		System.out.println(df);

//		dataFilter = DataFilter.createFromBase64(filter);
//
//		System.out.println(dataFilter);
//
//		String s = "Hello123..123";
//
//		byte[] sb = s.getBytes();
//
//		dataFilter.encodeData(sb, 0, sb.length);
//		System.out.println(new String(sb));
//		dataFilter.decodeData(sb, 0, sb.length);
//		System.out.println(new String(sb));

//		ByteArrayOutputStream byo = new ByteArrayOutputStream();
//
//		ObjectOutputStream obo = new ObjectOutputStream(byo);
//		obo.writeObject(dataFilter);
//
//		String payCityMapBase64 = new String((byo.toByteArray()));
//		
//		System.out.println(payCityMapBase64);

//		String s = "Hello,123,-123abcdefghijklmnopzrstuvwxyz";
//
//		DataFilter dataFilter = new DataFilter(true);
//
//		byte[] sbs = s.getBytes();
//
//		dataFilter.encodeData(sbs, 0, sbs.length);
//		
//		System.out.println(new String(sbs));
//		
//		dataFilter.decodeData(sbs, 0, sbs.length);
//		
//		String dec=new String(sbs);
//		System.out.println(dec+" ==>"+dec.equals(s));

//		Random rand=new Random((int)System.currentTimeMillis());
//		Integer[] ia={0,1,2,3,4,5,6,7,8,9};
//		ArrayList<Integer> list=new ArrayList<Integer>(Arrays.asList(ia));
//		System.out.println("Before shufflig: "+list);
//		Collections.shuffle(list,rand);
//		System.out.println("After shuffling: "+list);
//		System.out.print(Byte.MAX_VALUE);
//		System.out.print(Byte.MIN_VALUE);

//		for (byte b = Byte.MIN_VALUE; b <= Byte.MAX_VALUE; b++) {
//			byte enc = 0;
//			enc=(byte) -b;	
//			System.out.println(b + "=>" + enc);
//			if (b == Byte.MAX_VALUE) {
//				break;
//			}
//		}

	}
}
