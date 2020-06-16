/**
 *  第一个是固定的参数 即是你需要过滤的数据.例如下面的list,object
 *  过滤器参数,对应的是从第二个开始之后参数
 *
 *
 *  案例:
 *    //无参过滤 定义一个 Vue 全局的过滤器，名字叫做  msgFormat
      Vue.filter('msgFormat', function(msg) {
          // 字符串的  replace 方法，第一个参数，除了可写一个 字符串之外，还可以定义一个正则
          return msg.replace(/单纯/g, 'xx')
      })
      使用===> <p>{{ msg | msgFormat}}</p>

      //有参数过滤
      // 定义一个 Vue 全局的过滤器，名字叫做  msgFormat
      Vue.filter('msgFormat', function(msg, arg, arg2) {
          // 字符串的  replace 方法，第一个参数，除了可写一个 字符串之外，还可以定义一个正则
          return msg.replace(/单纯/g, arg+arg2)
      })
      使用===> <p>{{ msg | msgFormat('疯狂','--')}}</p>

 */



/**
 * 数组过滤器 例如：{{CHARGE | optionKV(section.charge)}}
 * @param list 例如：[{key:"C", value:"收费"},{key:"F", value:"免费"}]
 * @param key 例如：C
 * @returns {string} 例如：收费
 *
 * 这里的CHARGE 就是list,写在了前面
 */
let optionKVArray = (list, key) =>  {
  if (!list || !key) {
    return "";
  } else {
    let result = "";
    for (let i = 0; i < list.length; i++) {
      if (key === list[i]["key"]) {
        result = list[i]["value"];
      }
    }
    return result;
  }
};


/**
 * 对象类型过滤器
 * @param object
 * @param key
 * @returns {string}
 */
let optionKV = (object, key) =>  {
  if (!object || !key) {
    return "";
  } else {
    let result = "";
    for (let enums in object) {
      if (key === object[enums]["key"]) {
        result = object[enums]["value"];
      }
    }
    return result;
  }
};


/**
 * 时长格式化
 * @param value 例如：36000
 * @returns {string} 例如：10:00:00
 */
let formatSecond = (value) => {
  value = value || 0;
  let second = parseInt(value, 10); // 秒
  let minute = 0; // 分
  let hour = 0; // 小时
  if (second > 60) {
    // 当大于60秒时，才需要做转换
    minute = Math.floor(second / 60);
    second = Math.floor(second % 60);
    if (minute > 60) {
      hour = Math.floor(minute / 60);
      minute = Math.floor(minute % 60);
    }
  } else {
    // 小于60秒时，直接显示，不需要处理
  }
  let result = "" + PrefixInteger(second, 2) + "";
  // 拼上分钟
  result = "" + PrefixInteger(minute, 2) + ":" + result;
  // 拼上小时
  result = "" + PrefixInteger(hour, 2) + ":" + result;
  return result;
};

/**
 * 格式化指定长度，前面补0
 */
function PrefixInteger(num, length) {
  return (Array(length).join('0') + num).slice(-length);
}

/**
 * 文件字节转换成KB和MB
 * @param value
 * @returns {string}
 */
let formatFileSize = (value) => {
  value = value || 0;
  let result = 0;
  //判断
  if (value < 1024 * 100){//1KB
    result = Math.round(value / 1024 * 100) / 100 + "KB";
  }else if(value < 1024 * 1024 * 100){//1MB
    result = Math.round(value / 1024 / 1024 * 100) / 100 + "MB";
  }else if(value < 1024 * 1024 *  1024 * 100) {//1GB
    result = Math.round(value / 1024 / 1024  / 1024 * 100) / 100 + "GB";
  }else{
    return value;
  }
  return result;
}



/**
 * export命令用于规定模块的对外接口。

 一个模块就是一个独立的文件。该文件内部的所有变量，外部无法获取。
 如果你希望外部能够读取模块内部的某个变量，就必须使用export关键字输出该变量。
 下面是一个 JS 文件，里面使用export命令输出变量。
 */

export default {
  optionKV,
  formatSecond,
  formatFileSize
}
