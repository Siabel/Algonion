// import { Avatar, extendVariants, tv } from "@nextui-org/react";

// export const MyAvatar = extendVariants(Avatar, {
//   variants: {
//     color: {
//       default: {
//         backgroundColor: "#ffffff",
//         borderColor: "#000000",
//       },
//       bronze: {
//         backgroundColor: "#A05B34",
//         borderColor: "#A05B34",
//       },
//       silver: {
//         backgroundColor: "#C0C0C0",
//         borderColor: "#C0C0C0",
//       },
//       gold: {
//         backgroundColor: "#FFBF00",
//         borderColor: "#FFBF00",
//       },
//       emerald: {
//         backgroundColor: "#02B7AE",
//         borderColor: "#02B7AE",
//       },
//       diamond: {
//         backgroundColor: "#00FFFF",
//         borderColor: "#00FFFF",
//       },
//       master: {
//         backgroundColor: "#000000",
//         borderColor: "#ffffff",
//       },
//     },
//   },
//     radius: {
//       full: {
//         base: "rounded-full",
//       },
//     },
//     isBordered: {
//       true: {
//         base: "ring-2 ring-offset-2 ring-offset-background dark:ring-offset-background-dark",
//       },
//     },
//     isDisabled: {
//       true: {
//         base: "opacity-disabled",
//       },
//     },
//     isInGroup: {
//       true: {
//         base: [
//           "-ms-2 data-[hover=true]:-translate-x-3 transition-transform",
//           "data-[focus-visible=true]:-translate-x-3",
//         ],
//       },
//     },
//     isInGridGroup: {
//       true: {
//         base: "m-0 data-[hover=true]:translate-x-0",
//       },
//     },
//   },
//   defaultVariants: {
//     size: "md",
//     color: "default",
//     radius: "full",
//   },
//   compoundVariants: [
//     {
//       color: "default",
//       isBordered: 'true',
//       class: {
//         base: "ring-default",
//       },
//     },
//     {
//       color: "bronze",
//       isBordered: 'true',
//       class: {
//         base: "ring-bronze",
//       },
//     },
//     {
//       color: "silver",
//       isBordered: 'true',
//       class: {
//         base: "ring-silver",
//       },
//     },
//     {
//       color: "gold",
//       isBordered: 'true',
//       class: {
//         base: "ring-gold",
//       },
//     },
//     {
//       color: "platinum",
//       isBordered: 'true',
//       class: {
//         base: "ring-platinum",
//       },
//     },
//     {
//       color: "diamond",
//       isBordered: 'true',
//       class: {
//         base: "ring-diamond",
//       },
//     },
//     {
//       color: "master",
//       isBordered: true,
//       class: {
//         base: "ring-master",
//       },
//     },
//   ],
// });

// /**
//  * AvatarGroup wrapper **Tailwind Variants** component
//  *
//  * const classNames = avatarGroup({...})
//  *
//  * @example
//  * <div role="group" className={classNames())}>
//  *   // avatar elements
//  * </div>
//  */
// const avatarGroup = tv({
//   base: "flex items-center justify-center h-auto w-max-content",
//   variants: {
//     isGrid: {
//       true: "inline-grid grid-cols-4 gap-3",
//     },
//   },
// });

// // calculated classNames
// // src/components/avatar/src/use-avatar-group.ts
// // -ms-2 hover:-translate-x-0 ms-0

// export type AvatarGroupVariantProps = VariantProps<typeof avatarGroup>;
// export type AvatarVariantProps = VariantProps<typeof avatar>;
// export type AvatarSlots = keyof ReturnType<typeof avatar>;

// export {MyAvatar, avatarGroup};