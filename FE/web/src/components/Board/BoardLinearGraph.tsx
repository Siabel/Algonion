// https://apexcharts.com/docs/react-charts/
import { useEffect, useState } from "react";
import classes from './BoardLinearGraph.module.scss'
import Chart from "react-apexcharts";

function BoardLinearGraph(props: any) {
  const [state, setState] = useState({
    options: {
      stroke: {
        curve: 'stepline' as 'stepline',
        width: 2,
      },
      chart: {
        id: "basic-bar",
      },
      xaxis: {
        categories: props.categories,
      },
      colors: ['#9f22ff']
    },
    series: [
      {
        name: "points",
        data: props.data,
      },
    ],
  });

  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      options: {
        ...prevState.options,
        xaxis: {
          ...prevState.options.xaxis,
          categories: props.categories,
        },
      },
      series: [
        {
          ...prevState.series[0],
          data: props.data,
        },
      ],
    }));
  }, [props.categories, props.data]);

  return(
      <div className="app">
        <div className="row">
          <div className={`mixed-chart ${classes.chart}`}>
            <Chart
              options={state.options}
              series={state.series}
              type="line"
              width="700"
            />
          </div>
        </div>
      </div>
  );
}

export default BoardLinearGraph;