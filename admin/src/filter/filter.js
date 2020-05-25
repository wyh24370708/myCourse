/**
 * 数组过滤器 例如：{{CHARGE | optionKV(section.charge)}}
 * @param list 例如：[{key:"C", value:"收费"},{key:"F", value:"免费"}]
 * @param key 例如：C
 * @returns {string} 例如：收费
 *
 * 这里的CHARGE 就是list,写在了前面
 */
let optionKV = (list, key) =>  {
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
 * export命令用于规定模块的对外接口。

 一个模块就是一个独立的文件。该文件内部的所有变量，外部无法获取。
 如果你希望外部能够读取模块内部的某个变量，就必须使用export关键字输出该变量。
 下面是一个 JS 文件，里面使用export命令输出变量。


 */

export default {
    optionKV
}
