export const getAsset = (path: string) => {
  return new URL(`../assets/${path}`, import.meta.url).href;
};
export default getAsset;