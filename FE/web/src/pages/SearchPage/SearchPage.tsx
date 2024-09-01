import SearchBar from "../../components/Search/SearchBar.tsx";
import SearchResults from "../../components/Search/SearchResults.tsx";

import classes from "./SearchPage.module.scss";

function SearchPage() {
  return (
    <div>
      <div className={classes.page}>
        <div className={classes.search}>
          <SearchBar />
          <SearchResults />
        </div>
      </div>
    </div>
  );
}

export default SearchPage;
