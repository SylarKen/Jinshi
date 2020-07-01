﻿/**
 * 右键菜单模块
 * date:2019-02-08   License By http://easyweb.vip
 */
layui.define(["jquery"], function (a) {
    var c = layui.jquery;
    var b = {
        bind: function (e, d) {
            c(e).bind("contextmenu", function (f) {
                b.show(d, f.clientX, f.clientY, f);
                return false
            })
        }, show: function (f, d, k, h) {
            var g = "left: " + d + "px; top: " + k + "px;";
            var j = '<div class="ctxMenu" style="' + g + '">';
            j += b.getHtml(f, "");
            j += "   </div>";
            b.remove();
            c("body").append(j);
            var i = c(".ctxMenu");
            if (d + i.outerWidth() > b.getPageWidth()) {
                d -= i.outerWidth()
            }
            if (k + i.outerHeight() > b.getPageHeight()) {
                k = k - i.outerHeight();
                if (k < 0) {
                    k = 0
                }
            }
            i.css({"top": k, "left": d});
            b.setEvents(f, h);
            c(".ctxMenu-item").on("mouseenter", function (p) {
                p.stopPropagation();
                c(this).parent().find(".ctxMenu-sub").css("display", "none");
                if (!c(this).hasClass("haveMore")) {
                    return
                }
                var l = c(this).find(">a");
                var m = c(this).find(">.ctxMenu-sub");
                var o = l.offset().top - c("body,html").scrollTop();
                var n = l.offset().left + l.outerWidth() - c("body,html").scrollLeft();
                if (n + m.outerWidth() > b.getPageWidth()) {
                    n = l.offset().left - m.outerWidth()
                }
                if (o + m.outerHeight() > b.getPageHeight()) {
                    o = o - m.outerHeight() + l.outerHeight();
                    if (o < 0) {
                        o = 0
                    }
                }
                c(this).find(">.ctxMenu-sub").css({"top": o, "left": n, "display": "block"})
            })
        }, remove: function () {
            var h = parent.window.frames;
            for (var d = 0; d < h.length; d++) {
                var f = h[d];
                try {
                    f.layui.jquery("body>.ctxMenu").remove()
                } catch (g) {
                }
            }
            try {
                parent.layui.jquery("body>.ctxMenu").remove()
            } catch (g) {
            }
        }, setEvents: function (d, f) {
            c(".ctxMenu").off("click").on("click", "[lay-id]", function (h) {
                var i = c(this).attr("lay-id");
                var g = e(i, d);
                g.click && g.click(h, f)
            });

            function e(l, k) {
                for (var j = 0; j < k.length; j++) {
                    var h = k[j];
                    if (l == h.itemId) {
                        return h
                    } else {
                        if (h.subs && h.subs.length > 0) {
                            var g = e(l, h.subs);
                            if (g) {
                                return g
                            }
                        }
                    }
                }
            }
        }, getHtml: function (e, d) {
            var h = "";
            for (var f = 0; f < e.length; f++) {
                var g = e[f];
                g.itemId = "ctxMenu-" + d + f;
                if (g.subs && g.subs.length > 0) {
                    h += '<div class="ctxMenu-item haveMore" lay-id="' + g.itemId + '">';
                    h += "<a>";
                    if (g.icon) {
                        h += '<i class="' + g.icon + ' ctx-icon"></i>'
                    }
                    h += g.name;
                    h += '<i class="layui-icon layui-icon-right icon-more"></i>';
                    h += "</a>";
                    h += '<div class="ctxMenu-sub" style="display: none;">';
                    h += b.getHtml(g.subs, d + f);
                    h += "</div>"
                } else {
                    h += '<div class="ctxMenu-item" lay-id="' + g.itemId + '">';
                    h += "<a>";
                    if (g.icon) {
                        h += '<i class="' + g.icon + ' ctx-icon"></i>'
                    }
                    h += g.name;
                    h += "</a>"
                }
                h += "</div>";
                if (g.hr == true) {
                    h += "<hr/>"
                }
            }
            return h
        }, getCommonCss: function () {
            var d = ".ctxMenu, .ctxMenu-sub {";
            d += "        max-width: 250px;";
            d += "        min-width: 110px;";
            d += "        background: white;";
            d += "        border-radius: 2px;";
            d += "        padding: 5px 0;";
            d += "        white-space: nowrap;";
            d += "        position: fixed;";
            d += "        z-index: 2147483647;";
            d += "        box-shadow: 0 2px 4px rgba(0, 0, 0, .12);";
            d += "        border: 1px solid #d2d2d2;";
            d += "        overflow: visible;";
            d += "   }";
            d += "   .ctxMenu-item {";
            d += "        position: relative;";
            d += "   }";
            d += "   .ctxMenu-item > a {";
            d += "        font-size: 14px;";
            d += "        color: #666;";
            d += "        padding: 0 26px 0 35px;";
            d += "        cursor: pointer;";
            d += "        display: block;";
            d += "        line-height: 36px;";
            d += "        text-decoration: none;";
            d += "        position: relative;";
            d += "   }";
            d += "   .ctxMenu-item > a:hover {";
            d += "        background: #f2f2f2;";
            d += "        color: #666;";
            d += "   }";
            d += "   .ctxMenu-item > a > .icon-more {";
            d += "        position: absolute;";
            d += "        right: 5px;";
            d += "        top: 0;";
            d += "        font-size: 12px;";
            d += "        color: #666;";
            d += "   }";
            d += "   .ctxMenu-item > a > .ctx-icon {";
            d += "        position: absolute;";
            d += "        left: 12px;";
            d += "        top: 0;";
            d += "        font-size: 15px;";
            d += "        color: #666;";
            d += "   }";
            d += "   .ctxMenu hr {";
            d += "        background-color: #e6e6e6;";
            d += "        clear: both;";
            d += "        margin: 5px 0;";
            d += "        border: 0;";
            d += "        height: 1px;";
            d += "   }";
            d += "   .ctx-ic-lg {";
            d += "        font-size: 18px !important;";
            d += "        left: 11px !important;";
            d += "    }";
            return d
        }, getPageHeight: function () {
            return document.documentElement.clientHeight || document.body.clientHeight
        }, getPageWidth: function () {
            return document.documentElement.clientWidth || document.body.clientWidth
        },
    };
    c(document).off("click.ctxMenu").on("click.ctxMenu", function () {
        b.remove()
    });
    c(document).off("click.ctxMenuMore").on("click.ctxMenuMore", ".ctxMenu-item", function (d) {
        if (c(this).hasClass("haveMore")) {
            if (d !== void 0) {
                d.preventDefault();
                d.stopPropagation()
            }
        } else {
            b.remove()
        }
    });
    c("head").append('<style id="ew-css-ctx">' + b.getCommonCss() + "</style>");
    a("contextMenu", b)
});