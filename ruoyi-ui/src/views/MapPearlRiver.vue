<template>
  <div id="map" ref="map" class="map-container" style="height: 480px;"></div>
</template>

<script>
import L from 'leaflet';
import "leaflet/dist/leaflet.css";
import "leaflet.pm";
import "leaflet.pm/dist/leaflet.pm.css";
import Provider from '@/components/Map/chinatmsproviders';
// 标记点图标
import iconRetinaUrl from 'leaflet/dist/images/marker-icon-2x.png';
import iconUrl from 'leaflet/dist/images/marker-icon.png';
import shadowUrl from 'leaflet/dist/images/marker-shadow.png';

export default {
  name: "MapPearlRiver",
  data() {
    return {
      mapKey: 'd4e7681c282b16d557ab93e7ae3a5f45',
      myCenter: [23.125178, 113.280637],
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
        finishOn: null,
        // custom marker style (only for Marker draw)
        markerStyle: {
          opacity: 0.5,
          draggable: true,
        },
      },
      mapData: [
        {name:'飞来峡水库',tips:'这是飞来峡水库',lat:23.811745, lng:113.258097},
        {name:'东江水库',tips:'这是东江水库',lat:23.156917, lng:113.704533},
        {name:'三峡水库',tips:'这是三峡水库',lat:30.832745, lng:111.04057},
        {name:'三门峡水库',tips:'这是三门峡水库',lat:34.823012, lng:111.316256},
        {name:'小浪底水库',tips:'这是小浪底水库',lat:35.074657, lng:111.991404},
        {name:'密云水库',tips:'这是密云水库',lat:40.523289, lng:116.92017},
        {name:'大藤峡水库',tips:'这是大藤峡水库',lat:23.408051, lng:110.081791},
        {name:'珠江水利委员会',tips:'这是珠江水利委员会',lat:23.15022, lng:113.339318},
        {name:'长江水利委员会',tips:'这是长江水利委员会',lat:30.617849, lng:114.3144},
        {name:'黄河水利委员会',tips:'这是黄河水利委员会',lat:34.770371, lng:113.69231},
        {name:'海河水利委员会',tips:'这是海河水利委员会',lat:39.116233, lng:117.265547},
        {name:'松辽水利委员会',tips:'这是松辽水利委员会',lat:43.883655, lng:125.304507}
      ]
    };
  },
  mounted() {
    Provider(L);
    this.initMap()
  },
  methods: {
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
      const marker = L.marker([113.280637, -0.09]).addTo(map);
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
