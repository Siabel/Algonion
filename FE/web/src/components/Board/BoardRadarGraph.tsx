// https://apexcharts.com/docs/react-charts/

import { useState, useEffect } from "react";
import Chart from "react-apexcharts";

function BoardRadarGraph(props: any) {
    
  const [data, setData] = useState([]); 
  const [state, setState] = useState({
    series: [{
      name: 'Series 1',
      data: [],
    }],
    options: {
      dataLabels: {
        enabled: true
      },
      plotOptions: {
        radar: {
          size: 150,
          polygons: {
            strokeColors: '#e9e9e9',
            fill: {
              colors: ['#f8f8f8', '#fff']
            }
          }
        }
      },
      title: {
        text: ''
      },
      colors: ['#8F00FF'],
      markers: {
        colors: ['#8F00FF'],
        strokeColor: '#8F00FF',
        strokeWidth: 2,
      },
      tooltip: {
        y: {
          formatter: function(val: number) {
            return val.toString();
          }
        }
      },
      xaxis: {
        categories: ['수학', '구현', '그리디', '문자열', '자료 구조', '그래프 이론', 'DP', '기하학']
      },
      yaxis: {
        max: 30,
        tickAmount: 6,
        labels: {
          formatter: function(val: number) {
            return val.toString();
          }
        }
      },
    },
  });

  useEffect(() => {
    if (props.data) {
      setData(props.data);
    }
  }, [props.data]);

  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      series: [{
        ...prevState.series[0],
        data: data,
      }]
    }));
  }, [data]);
  
  return (
    <div className="app">
      <div className="row">
        <div className="mixed-chart">
          <Chart
            options={state.options}
            series={state.series}
            type="radar"
            width="600px"
          />
        </div>
      </div>
    </div>
  );
}

export default BoardRadarGraph;