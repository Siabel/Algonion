// https://apexcharts.com/docs/react-charts/
import { useEffect, useState } from "react";
import Chart from "react-apexcharts";

function BoardRoundGraph(props: any) {
    // console.log(props)

  const [series, setSeries] = useState<number[]>([]); 

  useEffect(() => {
    // console.log(props)
    if (props) {
      setSeries(props.series);
      // console.log(series)
    }
  }, [props]); 
  
  // console.log(props)
  const [state] = useState({
    options: {
      colors: ['#AD5600', '#435F7A', '#EC9A00', '#26E3A4', '#00B4FC', '#9F22FF'],
      labels: ['Bronze', 'Silver', 'Gold', 'Platinum', 'Diamond', 'Master'],
      dataLabels: {
        enabled: true,
      },
      plotOptions: {
        pie: {
          expandOnClick: false,
          donut: {
            size: '20%',
          },
        }
      },
    },
    // series: [44, 55, 41, 17, 15, 22],
  });
  

  return (
    <div className="app">
      <div className="row">
        <div className="mixed-chart">
          <Chart
            options={state.options}
            series={series}
            type="donut"
            width="500"
          />
        </div>
      </div>
    </div>
  );
}

export default BoardRoundGraph;