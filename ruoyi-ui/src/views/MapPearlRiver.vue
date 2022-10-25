<template>
  <div id="map" ref="map" class="map-container" style="height: 480px;"></div>
</template>

<script>
import L from 'leaflet';
import "leaflet/dist/leaflet.css";
import 'leaflet.chinatmsproviders'
import "leaflet.pm";
import "leaflet.pm/dist/leaflet.pm.css";
// 标记点图标
import iconRetinaUrl from 'leaflet/dist/images/marker-icon-2x.png';
import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

export default {
  name: "MapPearlRiver",
  data() {
    return {
      mapKey: 'd4e7681c282b16d557ab93e7ae3a5f45',
      myCenter: [113.264385,23.129112],
      marker: [],
      // 地图标注按钮选项
      options: {
        // snapping
        snappable: true,
        snapDistance: 20,
        // show tooltips
        tooltips: false,
        // allow snapping to the middle of segments
        snapMiddle: false,
        // self intersection
        allowSelfIntersection: true,
        // 连接线标记之间的线
        templineStyle: {
          color: 'red',
        },
        // 提示线从最后一个标记到鼠标光标的线
        hintlineStyle: {
          color: 'red',
          dashArray: [5, 5],
        },
        // show a marker at the cursor
        cursorMarker: false,
        // specify type of layer event to finish the drawn shape
        // example events: 'mouseout', 'dblclick', 'contextmenu'
        // List: http://leafletjs.com/reference-1.2.0.html#interactive-layer-click
        finishOn: null,
        // custom marker style (only for Marker draw)
        markerStyle: {
          opacity: 0.5,
          draggable: true,
        },
      },
      mapData: [
        {name:'巡检点1',tips:'这是巡检点1',lat:34.240651, lng:108.910072},
        {name:'巡检点2',tips:'这是巡检点2',lat:34.320755, lng:108.718494},
        {name:'巡检点3',tips:'这是巡检点3',lat:34.504293, lng:109.464432},
        {name:'巡检点4',tips:'这是巡检点4',lat:34.077687, lng:108.643203},
        {name:'巡检点5',tips:'这是巡检点5',lat:34.287006, lng:108.494648},
        {name:'巡检点6',tips:'这是巡检点6',lat:34.143634, lng:109.325729}
      ]
    };
  },
  mounted() {
    this.initMap()
  },
  methods: {
    // initMap() {
    //   this.map = L.map("map", {
    //     center: this.myCenter, // 中心位置 先纬度后经度
    //     zoom: 8, // 缩放等级
    //     attributionControl: true, // 版权控件
    //     zoomControl: true //缩放控件
    //   });
    //   L.tileLayer(
    //     "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    //       maxZoom: 19,
    //       attribution: '&copy; <a href="https://openstreetmap.org/copyright">OpenStreetMap contributors</a>',
    //     }
    //   ).addTo(this.map)
    // }
    // 初期加载地图
    initMap() {
      // 设置地图中心
      let map = L.map('map',{
        // 地图中心经纬度
        center: this.myCenter,
        // 当前缩放等级
        zoom: 10,
        // 显示地图缩放比例控件
        zoomControl: true,
        // 双击放大地图
        doubleClickZoom: true,
        // 是否去除右下角leaflet的logo标志
        attributionControl: false
      })

      // 添加地图搜索控件
      // var searchLayer = L.layerGroup().addTo(map);
      // map.addControl(new L.Control.Search({
      //   initial: false,
      //   callData: this.searchMap,
      //   layer: searchLayer,
      //   markerLocation: true
      // }));

      // 添加比例尺
      L.control.scale({maxWidth: 90, metric:true, imperial:false}).addTo(map);

      /**
       * 智图地图内容
       */
      const GeoqMap = L.tileLayer.chinaProvider('Geoq.Normal.Map', {
        maxZoom: 18,
        minZoom: 5
      });
      const PurplishBlue = L.tileLayer.chinaProvider('Geoq.Normal.PurplishBlue', {
        maxZoom: 18,
        minZoom: 5
      });
      const GeoqGray = L.tileLayer.chinaProvider('Geoq.Normal.Gray', {
        maxZoom: 18,
        minZoom: 5
      });
      const GeoqWarm = L.tileLayer.chinaProvider('Geoq.Normal.Warm', {
        maxZoom: 18,
        minZoom: 5
      });
      /**
       * 天地图内容
       */
      const NormalMap = L.tileLayer.chinaProvider('TianDiTu.Normal.Map', {
        maxZoom: 18,
        minZoom: 5
      }),
      NormalImage = L.tileLayer.chinaProvider('TianDiTu.Normal.Annotion', {
        maxZoom: 18,
        minZoom: 5
      }),
      SatelliteMap = L.tileLayer.chinaProvider('TianDiTu.Satellite.Map', {
        maxZoom: 18,
        minZoom: 5
      }),
      SatelliteImage = L.tileLayer.chinaProvider('TianDiTu.Satellite.Annotion', {
        maxZoom: 18,
        minZoom: 5
      }),
      terrainMap = L.tileLayer.chinaProvider('TianDiTu.Terrain.Map', {
        maxZoom: 18,
        minZoom: 5
      }),
      terrainImage = L.tileLayer.chinaProvider('TianDiTu.Terrain.Annotion', {
        maxZoom: 18,
        minZoom: 5
      });
      const normal = L.layerGroup([NormalMap, NormalImage]),
          image = L.layerGroup([SatelliteMap, SatelliteImage]),
          terrain = L.layerGroup([terrainMap, terrainImage]);
      /**
       * 高德地图
       */
      const Gaode = L.tileLayer.chinaProvider('GaoDe.Normal.Map', {
        maxZoom: 18,
        minZoom: 5
      });
      const Gaodimgem = L.tileLayer.chinaProvider('GaoDe.Satellite.Map', {
        maxZoom: 18,
        minZoom: 5
      });
      const Gaodimga = L.tileLayer.chinaProvider('GaoDe.Satellite.Annotion', {
        maxZoom: 18,
        minZoom: 5
      });
      const Gaodeimage = L.layerGroup([Gaodimgem, Gaodimga]);
      const OSMMap = L.tileLayer.chinaProvider('OSM.Normal.Map', {
        maxZoom: 18,
        minZoom: 5
      });
      const baseLayers = {
        "智图地图": GeoqMap,
        "智图午夜蓝": PurplishBlue,
        "智图灰色": GeoqGray,
        "智图暖色": GeoqWarm,
        "天地图": normal,
        "天地图影像": image,
        "地形影像": terrain,
        "高德地图": Gaode,
        "高德影像": Gaodeimage,
        "街道地图": OSMMap
      };

      // 添加地图绘制控件
      map.pm.setLang('zh');
      map.pm.addControls({
        position: "topleft",
        drawMarker: true,       // 绘制标记点
        drawPolyline: true,     // 绘制线条
        drawRectangle: true,	  // 绘制矩形
        drawPolygon: true,      // 绘制多边形
        drawCircle: true,       // 绘制圆圈
        drawCircleMarker: true, // 绘制圆形标记
        editMode: true,         // 编辑模式
        dragMode: true,         // 拖动模式
        cutPolygon: true,       // 剪切模式
        removalMode: true       // 清除模式
      })

      // 设置绘制完成后的线条颜色等
      map.pm.setPathOptions({
          color: "orange",
          illColor: "green",
          fillOpacity: 0.4,
      });

      map.pm.enableDraw('Marker', this.options);
      map.pm.enableDraw('Marker', { snappable: true });
      map.pm.disableDraw('Marker');
      map.pm.enableDraw('Line', this.options);
      map.pm.enableDraw('Line', { snappable: true});
      map.pm.disableDraw('Line');
      map.pm.enableDraw('Rectangle', this.options);
      map.pm.enableDraw('Rectangle', { snappable: true });
      map.pm.disableDraw('Rectangle');
      map.pm.enableDraw('Polygon', this.options);
      map.pm.enableDraw('Polygon', { snappable: true});
      map.pm.disableDraw('Polygon');
      map.pm.enableDraw('Circle', this.options);
      map.pm.enableDraw('Circle', { snappable: true });
      map.pm.disableDraw('Circle');
      map.pm.enableDraw('CircleMarker', this.options);
      map.pm.enableDraw('CircleMarker', { snappable: true });
      map.pm.disableDraw('CircleMarker');

      // 修改leaflet默认样式
      delete L.Icon.Default.prototype._getIconUrl;
      L.Icon.Default.mergeOptions({
        iconRetinaUrl: iconRetinaUrl,
        iconUrl: iconUrl,
        shadowUrl: shadowUrl
      });

      //设置ICON相关配置
      const marker = L.marker([34.2777998978, -0.09]).addTo(map);
      const Icon = L.divIcon({
        className: 'my-div-icon',// 自定义icon css样式
        iconSize: [15, 15] // 点大小
      });

      L.control.layers(baseLayers, null, {
        position: 'topright',
        collapsed: true
      }).addTo(map);
      this.getPointer(map);
      // 设置地图图层，可以按需引入；this.mapKey是自己的天地图key值
      L.tileLayer.chinaProvider('GaoDe.Satellite.Map',{maxZoom:18,minZoom:5,key:this.mapKey}).addTo(map);
      L.tileLayer.chinaProvider('GaoDe.Satellite.Annotion',{maxZoom:18,minZoom:5,key:this.mapKey}).addTo(map);

      // 鼠标移动时处理
      map.on('mouseup', e => {
        console.log(e,"鼠标移动时调用");
      })

      // 点击控件绘制时处理
      map.on("pm:drawstart", e => {
        switch (e.shape) {
          case 'Marker':
            console.log("选中的是绘制标记按钮");
            break;
          case 'Line':
            console.log("选中的是绘制线段按钮");
            break;
          case 'Rectangle':
            console.log("选中的是绘制长方形按钮");
            break;
          case 'Polygon':
            console.log("选中的是绘制多边形按钮");
            break;
          case 'Circle':
            console.log("选中的是绘制圆形按钮");
            break;
          case 'CircleMarker':
            console.log("选中的是绘制圆形标记按钮");
            break;
        }
        // 在地图上添加点击事件
        // map.on('click', e => {
        //   var lat = Number(e.latlng.lat).toFixed(6)
        //   var lng = Number(e.latlng.lng).toFixed(6)
        //   // 初次打点
        //   if(this.markersObj.length < 1){
        //     L.marker([lat, lng], {
        //       icon: Icon,
        //   	}).addTo(map)
        //   	// 添加点到地图中
        //   	this.markersObj.push(marker)
        //   }else{
        //   	// 再次打点更新点坐标
        //   	this.markersObj[0].setLatLng([lat, lng])
        //   }
        //   // 设置弹窗内容
        //   L.popup({maxWidth: 700, maxHeight: 200})
        //    .setLatLng(e.latlng)
        //    .setContent("点击坐标是" + '北纬:' + lat + ',' + '东经:'+ lng)
        //    .openOn(map);
        // })
      });
      // 取消绘制处理
      map.on("pm:drawend", e => {
        switch (e.shape) {
          case 'Marker':
            console.log("取消的是绘制标记按钮");
            break;
          case 'Line':
            console.log("取消的是绘制线段按钮");
            break;
          case 'Rectangle':
            console.log("取消的是绘制长方形按钮");
            break;
          case 'Polygon':
            console.log("取消的是绘制多边形按钮");
            break;
          case 'Circle':
            console.log("取消的是绘制圆形按钮");
            break;
          case 'CircleMarker':
            console.log("取消的是绘制圆形标记按钮");
            break;
        }
        // 关闭绘制功能
        document.getElementById("map").style.cursor = "grab";
      });
      // 绘制完成时处理
      map.on("pm:create", e => {
        console.log(e, "绘制完成时调用");
        if (e.shape == "Circle") {
          console.log(e.layer._latlng, e.layer._radius,"绘制坐标");
        } else {
          console.log(e.layer._latlng[0],"绘制坐标");
        }
      });
      // 清除图层时处理
      map.on('pm:globalremovalmodetoggled', e => {
        console.log(e, "清除图层时调用");
      })
      // 移动图层时处理
      map.on('pm:globaldragmodetoggled', e => {
        console.log(e, "移动图层时调用");
      })
    },
    // 取得坐标点位
    getPointer(map) {
      let _this = this // 防止变量冲突
      if (this.marker) {
        this.marker.map( res => {
          map.removeLayer(res)  // 清空标记
        })
      }
      this.mapData.map((res,index) => {
        let marker = L.marker([res.lat,res.lng]).addTo(map); // 设置标记经纬度
        map.addLayer(marker)  // 添加标记
         marker.setIcon(L.icon({ // 标记配置
          iconUrl: iconRetinaUrl, // 加载标记图
        }))
        if(index == 0){
          marker.bindPopup(res.tips).openPopup() // 默认展开标记点击弹窗
          marker.bindTooltip(res.name).openTooltip(); // 默认展开tooltip
        }else{
          marker.bindPopup(res.tips) // 标记点击弹窗
          marker.bindTooltip(res.name) // 标记悬浮弹窗
        }
        // 自定义其他事件
        marker.on('click', ()=>{
          console.log(res.name+'标记的可自定义点击事件');
        });
        this.marker.push(marker) // 保存标记，便于清空
      })
    }
  }
};
</script>

<style scoped lang="scss">
</style>
