Validator = {

    /**
     * 空校验
     * @param value
     * @param text
     * @returns {boolean}
     */
    require : function (value, text) {
        if (Tool.isEmpty(value)){
            Toast.warning(text+"不能为空");
            return false;
        }else{
            return true;
        }
    },

    /**
     * 长度校验
     * @param value
     * @param text
     * @param min
     * @param max
     * @returns {boolean}
     */
    length: function (value, text, min, max) {
        if (Tool.isLength(value,min,max)){
            return true;
        }else{
            Toast.warning(text + "长度" + min + "~" + max + "位");
            return false;
        }
    }
}