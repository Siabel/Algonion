import classes from './CodeLog.module.scss'
import hljs from 'highlight.js';
// import 'highlight.js/styles/atom-one-dark.css';
import './catppuccin-mocha.css';

interface CodeProps {
  code: string,
}

function CodeLog(props: CodeProps) {
  const html = hljs.highlightAuto(props.code).value;
  // console.log(html);

  return (
    <pre className={`${classes.code} hljs`}>
      <code className="hljs" dangerouslySetInnerHTML={{ __html: html }} />
    </pre>

  );
}

export default CodeLog;