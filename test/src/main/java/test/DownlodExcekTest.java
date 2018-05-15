package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class DownlodExcekTest {

	@Test
	public void test() {
		List<String> list = Arrays.asList("1", "1");
		System.out.println("-------------------------------------------------------");
		list.stream().forEach(e -> System.out.println(e));
		List<String> newList = list.stream().distinct().filter(l -> l.equals("1")).collect(Collectors.toList());

		System.out.println("-------------------------------------------------------");
		newList.stream().forEach(nl -> System.out.println(nl));

		List<Map<String, Object>> mapList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("1", "1");
		mapList.add(map);
		Map<String, Object> map0 = new HashMap<>();
		map0.put("1", "1");
		map0.put("2", "2");
		mapList.add(map0);
		mapList.stream().distinct().forEach(e -> System.out.println(e));

		System.out.println("-------------------------------------------------------");
		List<String> liStrings = Arrays.asList("123", "123").stream().distinct().collect(Collectors.toList());
		liStrings.stream().forEach(l -> System.out.println(l));

		System.out.println("-------------------------------------------------------");
		// 将Stream规约成List
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		// List<String> list1 = stream.collect(ArrayList::new, ArrayList::add,
		// ArrayList::addAll);// 方式１
		// List<String> list2 = stream.collect(Collectors.toList());// 方式2
		// System.out.println(list2);

		System.out.println("-------------------------------------------------------");
		// 使用toCollection()指定规约容器的类型
//		ArrayList<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));// (3)
		HashSet<String> hashSet = stream.collect(Collectors.toCollection(HashSet::new));// (4)
		hashSet.stream().forEach(hs -> System.out.println(hs));
		
	}

}
