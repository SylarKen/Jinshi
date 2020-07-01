﻿/**
 * 步骤条模块
 * date:2020-02-16   License By http://easyweb.vip
 */
layui.define(["element"], function (b) {
    var d = layui.jquery;
    var c = layui.element;
    if (d("#ew-css-steps").length <= 0) {
        layui.link(layui.cache.base + "steps/steps.css")
    }
    var a = {};
    a.next = function (f) {
        a.checkLayId(f);
        var h = d('[lay-filter="' + f + '"]');
        var g = h.children(".layui-tab-title").children("li");
        var e = g.filter(".layui-this").next();
        if (e.length <= 0) {
            e = g.first()
        }
        c.tabChange(f, e.attr("lay-id"))
    };
    a.prev = function (f) {
        a.checkLayId(f);
        var h = d('[lay-filter="' + f + '"]');
        var g = h.children(".layui-tab-title").children("li");
        var e = g.filter(".layui-this").prev();
        if (e.length <= 0) {
            e = g.last()
        }
        c.tabChange(f, e.attr("lay-id"))
    };
    a.go = function (f, e) {
        a.checkLayId(f);
        var h = d('[lay-filter="' + f + '"]');
        var g = h.children(".layui-tab-title").children("li");
        c.tabChange(f, g.eq(e).attr("lay-id"))
    };
    a.checkLayId = function (e) {
        var g = d('.layui-steps[lay-filter="' + e + '"]');
        var f = g.children(".layui-tab-title").children("li");
        if (f.first().attr("lay-id") === undefined) {
            f.each(function (h) {
                d(this).attr("lay-id", "steps-" + h)
            })
        }
        g.find(".layui-tab-bar").remove();
        g.removeAttr("overflow")
    };
    d(document).off("click.steps").on("click.steps", "[data-steps]", function () {
        var g = d(this);
        var f = g.parents(".layui-steps").first().attr("lay-filter");
        var e = g.data("steps");
        if (e === "next") {
            a.next(f)
        } else {
            if (e === "prev") {
                a.prev(f)
            } else {
                if (e === "go") {
                    a.go(f, g.data("go"))
                }
            }
        }
    });
    b("steps", a)
});