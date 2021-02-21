package com.ll;

import cn.hutool.core.text.StrSpliter;
import com.ll.util.MarkdownUtils;
import org.junit.jupiter.api.Test;

public class HutoolTest {
    @Test
    public void test() {
        String s = "+ 添加/修改/获取/删除\n" +
                "\n" +
                "```bash\n" +
                "127.0.0.1:6379> hset user name zhangsan\n" +
                "(integer) 1\n" +
                "127.0.0.1:6379> hset user age 38\n" +
                "(integer) 1\n" +
                "127.0.0.1:6379> hset user weight 80\n" +
                "(integer) 1\n" +
                "127.0.0.1:6379> hgetall user\n" +
                "1) \"name\"\n" +
                "2) \"zhangsan\"\n" +
                "3) \"age\"\n" +
                "4) \"38\"\n" +
                "5) \"weight\"\n" +
                "6) \"80\"\n" +
                "127.0.0.1:6379> hget user name\n" +
                "\"zhangsan\"\n" +
                "127.0.0.1:6379> hdel user weight\n" +
                "(integer) 1\n" +
                "127.0.0.1:6379> hgetall user\n" +
                "1) \"name\"\n" +
                "2) \"zhangsan\"\n" +
                "3) \"age\"\n" +
                "4) \"38\"  \n" +
                "```\n" +
                "+ 增改多个数据\n" +
                "```bash\n" +
                "127.0.0.1:6379> hmget user name age\n" +
                "1) \"zhangsan\"\n" +
                "2) \"38\"\n" +
                "127.0.0.1:6379> hmset user name zhangsanfeng weight 68  //hmset key field value [field value ...]\n" +
                "OK\n" +
                "127.0.0.1:6379> hgetall user\n" +
                "1) \"name\"\n" +
                "2) \"zhangsanfeng\"\n" +
                "3) \"age\"\n" +
                "4) \"38\"\n" +
                "5) \"weight\"\n" +
                "6) \"68\"\n" +
                "127.0.0.1:6379> hlen user //查看field数量\n" +
                "(integer) 3\n" +
                "127.0.0.1:6379> hexists user age //查看field是否存在\n" +
                "(integer) 1\n" +
                "```\n" +
                "\n" +
                "### hash类型扩展操作\n" +
                "\n" +
                "+ 获取field的key和value\n" +
                "\n" +
                "```bash\n" +
                "127.0.0.1:6379> hkeys user\n" +
                "1) \"name\"\n" +
                "2) \"age\"\n" +
                "3) \"weight\"\n" +
                "127.0.0.1:6379> hvals user\n" +
                "1) \"zhangsanfeng\"\n" +
                "2) \"38\"\n" +
                "3) \"68\"\n" +
                "```\n" +
                "\n" +
                "+ 数值增减\n" +
                "\n" +
                "```bash\n" +
                "127.0.0.1:6379> hincrby user age 1               //传负数就是减少\n" +
                "(integer) 39\n" +
                "127.0.0.1:6379> hincrbyfloat user age 1.5\n" +
                "\"40.5\"\n" +
                "```\n" +
                "\n" +
                "```bas\n" +
                "hsetnx key field value //存在就不改变，不存在则添加\n" +
                "```\n" +
                "\n" +
                "### list类型基础操作\n" +
                "\n" +
                "`底层双向链表实现`\n" +
                "\n" +
                "+ 增改/获取/删除\n" +
                "\n" +
                "```bash\n" +
                "\n" +
                "127.0.0.1:6379> lpush list1 huawei\n" +
                "(integer) 1\n" +
                "127.0.0.1:6379> lpush list1 apple\n" +
                "(integer) 2\n" +
                "127.0.0.1:6379> lpush list1 microsoft\n" +
                "(integer) 3\n" +
                "127.0.0.1:6379> lrange list1 0 2    //从0查到2\n" +
                "1) \"microsoft\"\n" +
                "2) \"apple\"\n" +
                "3) \"huawei\"\n" +
                "\n" +
                "127.0.0.1:6379> rpush list2 a b c\n" +
                "(integer) 3\n" +
                "127.0.0.1:6379> lrange list2 0 2\n" +
                "1) \"a\"\n" +
                "2) \"b\"\n" +
                "3) \"c\"\n" +
                "127.0.0.1:6379> lrange list2 0 -1   //从0查到-1(倒数第一个)\n" +
                "1) \"a\"\n" +
                "2) \"b\"\n" +
                "3) \"c\"\n" +
                "\n" +
                "//查单个\n" +
                "127.0.0.1:6379> lindex list1 0\n" +
                "\"microsoft\"\n" +
                "127.0.0.1:6379> lindex list1 2\n" +
                "\"huawei\"\n" +
                "\n" +
                "//查长度\n" +
                "127.0.0.1:6379> llen list1\n" +
                "(integer) 3\n" +
                "\n" +
                "//删除\n" +
                "127.0.0.1:6379> lpush list3 a b c\n" +
                "(integer) 3\n" +
                "127.0.0.1:6379> lpop list3\n" +
                "\"c\"\n" +
                "127.0.0.1:6379> lpop list3\n" +
                "\"b\"\n" +
                "```\n" +
                "\n" +
                "### list类型扩展操作\n" +
                "\n" +
                "+ 规定时间内获取并移除数据（等待数据输入）\n" +
                "\n" +
                "```bash\n" +
                "blpop key1 [key2] timeout\n" +
                "brpop key1 [key2] timeout\n" +
                "```";

//        String[] strings = StrSpliter.splitByLength(s, 300);
//        System.out.println(strings[0]);

        System.out.println(MarkdownUtils.markdownTOText(s));



    }
}
