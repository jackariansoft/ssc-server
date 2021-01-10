/* global easyloader, detailview */
function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}
function formatMoney(amount, decimalCount = 2, decimal = ",", thousands = ".") {
    try {
        decimalCount = Math.abs(decimalCount);
        decimalCount = isNaN(decimalCount) ? 2 : decimalCount;
        const negativeSign = amount < 0 ? "-" : "";
        let i = parseInt(amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)).toString();
        let j = (i.length > 3) ? i.length % 3 : 0;
        return negativeSign + (j ? i.substr(0, j) + thousands : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousands) + (decimalCount ? decimal + Math.abs(amount - i).toFixed(decimalCount).slice(2) : "");
    } catch (e) {
        console.log(e);
}
}
function convertDate(inputFormat) {
    function pad(s) {
        return (s < 10) ? '0' + s : s;
    }
    var date = moment(inputFormat, "DD/MM/YYYY");
    //console.log(date);
    return date.format('DD-MM-YYYY');
}

function getParameterByName(name, url) {
    if (!url)
        url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
    if (!results)
        return null;
    if (!results[2])
        return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function populateUrlparams() {
    var match,
            pl = /\+/g, // Regex for replacing addition symbol with a space
            search = /([^&=]+)=?([^&]*)/g,
            decode = function (s) {
                return decodeURIComponent(s.replace(pl, " "));
            },
            query = window.location.search.substring(1);
    var urlParams = {};
    while ((match = search.exec(query))) {
        urlParams[decode(match[1])] = decode(match[2]);
    }
    return urlParams;
}

class MyMap {
    constructor() {
        this.keys = new Array();
        this.data = new Object();
        this.put = function(key, value) {

            if (this.keys[key] === null || this.keys[key] === undefined) {
                this.keys.push(key);
                this.data[key] = value;
            }

        };
        this.get = function(key) {
            return this.data[key];
        };
        this.remove = function(key) {
            this.keys.remove(key);
            this.data[key] = null;
        };
        this.clear = function() {
            this.keys = new Array();
            this.data = new Object();
        };
        this.each = function(fn) {
            if (typeof fn !== 'function') {
                return;
            }
            var len = this.keys.length;
            for (var i = 0; i < len; i++) {
                var k = this.keys[i];
                fn(k, this.data[k], i);
            }
        };
        this.entrys = function() {
            var len = this.keys.length;
            var entrys = new Array(len);
            for (var i = 0; i < len; i++) {
                entrys[i] = {
                    key: this.keys[i],
                    value: this.data[i]
                };
            }
            return entrys;
        };
        this.isEmpty = function() {
            return this.keys.length === 0;
        };
        this.size = function() {
            return this.keys.length;
        };

    }
};



function buildDatabaseQueryString() {
    var staticLink = '';
    var site_option = $('#channel').val();
    var entrys = new Array();
    if (site_option === "all") {
        staticLink += 'all';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;

            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}
function buildQueryStringChannelExclude() {
    var staticLink = '';
    var site_option = $('#channelExcl').val();
    var entrys = new Array();
    if (site_option === "all") {
        staticLink += 'all';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;

            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}


function getChunkStringUrl(field) {
    var fd = $("#" + field);
    if (fd.val() === '') {
        return '';
    }
    var staticLink = "&";
    staticLink += field + "=" + fd.val();
    return staticLink;
}
function getChunkStringUrlNotEnd(field) {
    var fd = $("#" + field);
    if (fd.val() === '') {
        return '';
    }
    return field + "=" + fd.val();
}
/**
 * 
 * @param {type} urlParams
 * @returns {undefined}
 */
function setFieldFormOrdini(urlParams) {

    $('#start').datepicker("setDate", urlParams.start);
    $('#end').datepicker("setDate", urlParams.end);

    $("#logistic-status option").filter(function () {
        return $(this).val() === urlParams.logistic-status;
    }).prop('selected', true);
    $("#channel option").filter(function () {
        return $(this).val() === urlParams.channel;
    }).prop('selected', true);
    
}
/**
 * 
 * @param {type} page
 * @param {type} pager
 * @returns {void}
 */
function setQueryStringOrdini(page, pager) {

    var staticLink = getChunkStringUrlNotEnd("start");
    staticLink += getChunkStringUrl("end");
    staticLink += getChunkStringUrl("channel");   
    staticLink += getChunkStringUrl("logistic-status");
    var options = pager.pagination('options');
    staticLink += '&page=' + (options.pageNumber === 0 ? 1 : options.pageNumber);
    staticLink += '&pageSize=' + options.pageSize;

    window.history.pushState("object or string", "Title", "/ssc/" + page + "?" + staticLink.trim());

}


function buildDatabaseQueryString(id) {
    var staticLink = '';
    var site_option = $('#'+id+'').tagbox('getValues');
    var entrys = new Array();
    if (site_option === "all") {
        staticLink += 'all';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;

            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}
function buildQueryStringChannelExclude(id) {
    var staticLink = '';
    var site_option = $('#'+id+'').tagbox('getValues');
    var entrys = new Array();
    if (site_option === "none") {
        staticLink += 'none';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;

            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}
function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
            if (!s) return new Date();
            var ss = (s.split('-'));
            var y = parseInt(ss[0],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[2],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d);
            } else {
                return new Date();
            }
        }
